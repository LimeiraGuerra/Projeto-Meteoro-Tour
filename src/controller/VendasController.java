package controller;

import database.dao.AssentosTrechoLinhaDAO;
import database.dao.InfoRelatorioDAO;
import database.dao.LinhaDAO;
import database.dao.TrechoLinhaDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import model.entities.Passagem;
import model.usecases.AutoCompleteUC;
import model.usecases.EmitirRelatoriosUC;
import view.loader.*;
import model.entities.Viagem;
import model.usecases.GerarViagensUC;
import view.util.AlertWindow;
import view.util.DataValidator;
import view.util.TipoEspecial;
import view.util.sharedCodes.AutoCompleteComboBoxListener;

import java.io.File;
import java.util.*;

public class VendasController {

    @FXML ComboBox<String> cBoxOrigem, cBoxDestino;
    @FXML Menu menuGerenciar;
    @FXML AnchorPane popupReagendamento;
    @FXML DatePicker datePickerSaida;
    @FXML MenuItem menuOptPassagens;
    @FXML Menu menuRelatorio;
    @FXML ToggleGroup clienteEspecial;
    @FXML TableView<Viagem> tableViagens;
    @FXML TableColumn<Viagem, String> colLinha, colHorarioSaida;

    private Scene scene;
    private TipoEspecial clientType = TipoEspecial.NÃO;
    private GerarViagensUC gerarViagensUC;
    private EmitirRelatoriosUC emitirRelatoriosUC;
    private AutoCompleteUC autoCompleteUC;
    private ObservableList<Viagem> tableDataViagens;
    private Viagem selectedViagem;
    private String messageHead, messageBody;
    private Passagem passagemReagendamento;
    private boolean modeReagendamento = false;
    private ObservableList<String> cityNames;

    public VendasController() {
        this.autoCompleteUC = new AutoCompleteUC(new TrechoLinhaDAO(), new LinhaDAO());
        this.gerarViagensUC = new GerarViagensUC(new LinhaDAO(),
                new TrechoLinhaDAO(),
                new AssentosTrechoLinhaDAO());
        this.emitirRelatoriosUC = new EmitirRelatoriosUC(new InfoRelatorioDAO());
    }

    @FXML
    private void initialize(){
        this.bindDataListToTable();
        this.bindColumnsToValues();
        this.setAutoComplete();
    }

    private void setAutoComplete(){
        this.cityNames = FXCollections.observableArrayList();
        this.getCityNames();
        this.setValuesToComboBoxes();
        this.setAutoCompleteListeners();
    }

    private void getCityNames(){
        this.cityNames.setAll(autoCompleteUC.getCityNames());
    }

    private void setAutoCompleteListeners(){
        new AutoCompleteComboBoxListener<>(this.cBoxOrigem);
        new AutoCompleteComboBoxListener<>(this.cBoxDestino);
    }

    private void setValuesToComboBoxes(){
        this.cBoxOrigem.setItems(this.cityNames);
        this.cBoxDestino.setItems(this.cityNames);
    }

    private void bindDataListToTable() {
        this.tableDataViagens = FXCollections.observableArrayList();
        this.tableViagens.setItems(this.tableDataViagens);
    }

    private void bindColumnsToValues(){
        this.colLinha.setCellValueFactory(new PropertyValueFactory<>("linhaName"));
        this.colHorarioSaida.setCellValueFactory(new PropertyValueFactory<>("horarioSaida"));
    }

    public void setAdminPrivileges() {
        this.menuGerenciar.setDisable(false);
        this.menuRelatorio.setDisable(false);
    }

    public void setModeReagendamento() {
        this.setInfoInFields();
        this.toggleModeReagendamento(true);
    }

    public void cancelModeReagendamento() {
        this.toggleModeReagendamento(false);
        this.clearViewFromResults();
    }

    private void setInfoInFields(){
        this.cBoxOrigem.setValue(this.passagemReagendamento.getViagem().getCidadeOrigem());
        this.cBoxDestino.setValue(this.passagemReagendamento.getViagem().getCidadeDestino());
    }

    private void toggleModeReagendamento(boolean mode) {
        this.modeReagendamento = mode;
        this.cBoxOrigem.setDisable(mode);
        this.cBoxDestino.setDisable(mode);
        this.popupReagendamento.setVisible(mode);
    }

    public void openBuscarPassagens(ActionEvent actionEvent) {
        this.clearViewFromResults();
        PassagemLoader janelaPassagens = new PassagemLoader();
        janelaPassagens.start();
        this.passagemReagendamento = janelaPassagens.getPassagemReagendamento();
        if (this.passagemReagendamento != null) {
            this.setModeReagendamento();
        }
    }

    public void openTrecho(ActionEvent actionEvent) {
        this.clearViewFromResults();
        TrechoLoader janelaTrecho = new TrechoLoader();
        janelaTrecho.start();
    }

    public void openLinha(ActionEvent actionEvent) {
        this.clearViewFromResults();
        LinhaLoader janelaLinha = new LinhaLoader();
        janelaLinha.start();
        this.getCityNames();
    }

    public void checkToggle() {
        RadioButton selectedRadioButton = (RadioButton) clienteEspecial.getSelectedToggle();
        this.toggleClientType(selectedRadioButton.getId());
    }

    private void toggleClientType(String id){
        if (id.equals("rdIdoso")){
            this.setClientType(TipoEspecial.IDOSO);
            this.toggleSpecialSeats(false);
        }else {
            if (id.equals("rdDeficiente"))
                this.setClientType(TipoEspecial.DEFICIENTE);
            else
                this.setClientType(TipoEspecial.NÃO);
            this.toggleSpecialSeats(true);
        }
    }

    private void toggleSpecialSeats(boolean mode){
        this.getButtonByCSSId("#03").setDisable(mode);
        this.getButtonByCSSId("#04").setDisable(mode);
        this.disableUnavailableSeats();
    }

    public void searchForViagens(ActionEvent actionEvent) {
        Date data = DataValidator.LocalDateConverter(this.datePickerSaida.getValue());
        String cidadeOrigem = DataValidator.txtInputVerifier(this.getValueComboBox(this.cBoxOrigem));
        String cidadeDestino = DataValidator.txtInputVerifier(this.getValueComboBox(this.cBoxDestino));
        if (this.checkInputsValues(data, cidadeOrigem, cidadeDestino))
            this.verifyTimeOfResults(this.gerarViagensUC.searchForViagens(data, cidadeOrigem, cidadeDestino));
        else {
            this.clearViewFromResults();
            this.messageHead = "Parâmetros de pesquisa inválidos ou nulos!";
            AlertWindow.errorAlert(messageBody,messageHead);
        }
        this.selectedViagem = null;
    }

    private String getValueComboBox(ComboBox<String> comboBox){
        return comboBox.getSelectionModel().getSelectedItem();
    }

    private void clearTable(){
        this.showResultsToTable(new ArrayList<>());
    }

    private void clearViewFromResults(){
        this.selectedViagem = null;
        this.clearTable();
        this.resetSeats();
        this.checkToggle();
    }

    private void verifyTimeOfResults(List<Viagem> viagens){
        List<Viagem> viagensTimeFilter = new ArrayList<>();
        for (Viagem v : viagens) {
            if (this.getSystemTime().compareTo(v.getData()) <= 0)
                viagensTimeFilter.add(v);
        }
        this.clearViewFromResults();
        if (viagensTimeFilter.isEmpty()) {
            this.messageHead = "Busca não encontrou nenhum resultado válido!";
            this.messageBody = "Reveja os parâmetros informados.";
            AlertWindow.informationAlerta(messageBody, messageHead);
        }else this.showResultsToTable(viagensTimeFilter);
    }

    private Date getSystemTime(){
        return Calendar.getInstance().getTime();
    }

    private boolean checkInputsValues(Date data, String cidadeOrigem, String cidadeDestino) {
        StringBuilder st = new StringBuilder();
        if (data == null){st.append("Campo data inválido.\n");}
        if (cidadeOrigem == null){st.append("Campo cidade origem inválido.\n");}
        if (cidadeDestino == null){st.append("Campo cidade destino inválido.\n");}
        this.messageBody = st.toString();
        return st.length() == 0;
    }

    private void showResultsToTable(List<Viagem> viagens){
        this.tableDataViagens.setAll(viagens);
    }

    private void resetSeats(){
        for (int i = 1; i < 45; i++) {
            Button bt = this.getButtonByCSSId("#".concat(String.format("%02d", i)));
            bt.setDisable(false);
        }
    }

    private void disableUnavailableSeats(){
        if(this.selectedViagem != null) {
            Iterator<String> itAV = this.selectedViagem.getAssentosVendidosViagem();
            while (itAV.hasNext()) {
                Button bt = this.getButtonByCSSId("#".concat(itAV.next()));
                bt.setDisable(true);
            }
        }
    }

    private void toggleStateAndVisualOfSeats(){
        this.resetSeats();
        this.disableUnavailableSeats();
        this.checkToggle();
    }

    public void selectViagemForSale(MouseEvent mouseEvent) {
        if(mouseEvent.getClickCount() == 1) {
            this.selectedViagem = this.tableViagens.getSelectionModel().getSelectedItem();
            if (this.selectedViagem != null)
                this.toggleStateAndVisualOfSeats();
        }
    }

    private Button getButtonByCSSId(String selector){
        return (Button) this.scene.lookup(selector);
    }

    public void purchaseClickedSeat(ActionEvent actionEvent) {
        if (this.selectedViagem != null) {
            Button btn = (Button) actionEvent.getSource();
            FinalizacaoVendaLoader janelaFinal;
            if (this.modeReagendamento)
                janelaFinal = new FinalizacaoVendaLoader(this.passagemReagendamento);
            else janelaFinal = new FinalizacaoVendaLoader();
            janelaFinal.start(this.selectedViagem, this.getClientType(), btn.getId());
            if (janelaFinal.isSoldSuccess()) {
                if (this.modeReagendamento) this.cancelModeReagendamento();
                else this.markSoldSeat(btn.getId());
            }
        }
    }

    private void markSoldSeat(String seatId){
        this.selectedViagem.addAssentoVendidoViagem(seatId);
        this.toggleStateAndVisualOfSeats();
    }

    public void openOnibus(ActionEvent actionEvent){
        OnibusLoader janelaOnibus = new OnibusLoader();
        janelaOnibus.start();

    }

    public void openFunc(ActionEvent actionEvent) {
        FuncionarioLoader janelaFunc = new FuncionarioLoader();
        janelaFunc.start();
    }

    public void openRelatorios(ActionEvent actionEvent) {
        RelatorioLoader janelaRelatorio = new RelatorioLoader();
        janelaRelatorio.start();
    }

    public void generateDailyReport(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("CSV Files", "*.csv"));
        File file = fileChooser.showSaveDialog(this.scene.getWindow());
        this.emitirRelatoriosUC.exportDailyReport(file, this.getSystemTime());
    }

    public TipoEspecial getClientType() {
        return clientType;
    }

    public void setClientType(TipoEspecial clientType) {
        this.clientType = clientType;
    }

    public Scene getScene() {
        return scene;
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }

}

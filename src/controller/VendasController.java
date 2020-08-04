package controller;

import database.dao.ViagemDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import view.loader.*;
import model.entities.Viagem;
import model.usecases.GerarViagensUC;
import view.util.TextFieldValidator;
import view.util.TipoEspecial;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

public class VendasController {
    
    @FXML Menu menuGerenciar;
    @FXML AnchorPane popupReagendamento;
    @FXML TextField txtFieldOrigem, txtFieldDestino;
    @FXML DatePicker datePickerSaida;
    @FXML MenuItem menuOptPassagens;
    @FXML ToggleGroup clienteEspecial;
    @FXML TableView<Viagem> tableViagens;
    @FXML TableColumn<Viagem, String> colLinha, colHorarioSaida;

    private Scene scene;
    private TipoEspecial clientType = TipoEspecial.NÃO;
    private GerarViagensUC gerarViagensUC;
    private ObservableList<Viagem> tableDataViagens;
    private Viagem selectedViagem;
    private String messageHead;
    private String messageBody;

    public VendasController() {
        this.gerarViagensUC = new GerarViagensUC(ViagemDAO.getInstancia());
    }

    @FXML
    private void initialize(){
        this.bindDataListToTable();
        this.bindColumnsToValues();
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
    }

    public void setModeReagendamento() {
        this.setInfoInFields();
        this.toggleModeReagendamento(true);
    }

    public void cancelModeReagendamento(ActionEvent actionEvent) {
        this.toggleModeReagendamento(false);
    }

    private void setInfoInFields(){
        //txtFieldOrigem.setText(this.passagemReagendamento.getViagem().getCidadeOrigem());
        //txtFieldDestino.setText(this.passagemReagendamento.getViagem().getCidadeDestino());
    }

    private void toggleModeReagendamento(boolean mode) {
        this.txtFieldOrigem.setDisable(mode);
        this.txtFieldDestino.setDisable(mode);
        this.popupReagendamento.setVisible(mode);
    }

    public void openBuscarPassagens(ActionEvent actionEvent) {
        PassagemLoader janelaPassagens = new PassagemLoader();
        janelaPassagens.start();
        //Abre a modal Buscar Passagem
        //Apenas teste
        this.setModeReagendamento();
    }

    public void openTrecho(ActionEvent actionEvent) {
        TrechoLoader janelaTrecho = new TrechoLoader();
        janelaTrecho.start();
    }

    public void openLinha(ActionEvent actionEvent) {
        LinhaLoader janelaLinha = new LinhaLoader();
        janelaLinha.start();
    }

    public void checkToggle() {
        RadioButton selectedRadioButton = (RadioButton) clienteEspecial.getSelectedToggle();
        this.toggleClientType(selectedRadioButton.getId());
    }
    private void toggleClientType(String id){
        if (id.equals("rdIdoso")){
            this.setClientType(TipoEspecial.IDOSO);
            this.toggleSpecialSits(false);
        }else {
            if (id.equals("rdDeficiente"))
                this.setClientType(TipoEspecial.DEFICIENTE);
            else
                this.setClientType(TipoEspecial.NÃO);
            this.toggleSpecialSits(true);
        }
    }

    private void toggleSpecialSits(boolean mode){
        this.getButtonByCSSId("#btnAssento03").setDisable(mode);
        this.getButtonByCSSId("#btnAssento04").setDisable(mode);
        this.disableUnavailableSits();
    }

    public void searchForViagens(ActionEvent actionEvent) {
        Date data = TextFieldValidator.LocalDateConverter(this.datePickerSaida.getValue());
        String cidadeOrigem = TextFieldValidator.txtInputVerifier(this.txtFieldOrigem.getText());
        String cidadeDestino = TextFieldValidator.txtInputVerifier(this.txtFieldDestino.getText());
        if (this.checkInputsValues(data, cidadeOrigem, cidadeDestino)) {
            List<Viagem> viagens = this.gerarViagensUC.searchForViagens(data, cidadeOrigem, cidadeDestino);
            this.verifyTimeOfResults(viagens);
        }
        else {
            this.messageHead = "Parâmetros de pesquisa inválidos ou nulos!";
            this.errorAlert();
        }
    }

    private void errorAlert(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erro!");
        this.modalAlert(alert);
    }

    private void informationAlert(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Aviso!");
        this.modalAlert(alert);
    }

    private void modalAlert(Alert alert){
        alert.setHeaderText(this.messageHead);
        alert.setContentText(this.messageBody);
        alert.showAndWait();
    }

    private void verifyTimeOfResults(List<Viagem> viagens){
        List<Viagem> viagensTimeFilter = new ArrayList<>();
        for (Viagem v : viagens) {
            if (this.getSystemTime().compareTo(v.getData()) <= 0) {
                viagensTimeFilter.add(v);
            }
        }
        this.showResultsToTable(viagensTimeFilter);
        if (viagensTimeFilter.isEmpty()) {
            this.messageHead = "Busca não encontrou nenhum resultado válido!";
            this.messageBody = "Reveja os parâmetros informados.";
            this.informationAlert();
        }
    }

    private Date getSystemTime(){
        return Calendar.getInstance().getTime();
    }

    private boolean checkInputsValues(Date data, String cidadeOrigem, String cidadeDestino) {
        StringBuilder st = new StringBuilder();
        if (data == null){st.append("Campo data inválido.\n");}
        if (cidadeOrigem == null){st.append("Campo cidade origem inválido.\n");}
        if (cidadeDestino == null){st.append("Campo cidade destino inválido.");}
        this.messageBody = st.toString();
        return st.length() == 0;
    }

    private void showResultsToTable(List<Viagem> viagens){
        this.tableDataViagens.setAll(viagens);
    }

    private void resetSits(){
        for (int i = 1; i < 45; i++) {
            Button bt = this.getButtonByCSSId("#btnAssento" + String.format("%02d", i));
            bt.setDisable(false);
        }
    }

    private void disableUnavailableSits(){
        if(this.selectedViagem != null) {
            Set<String> assentosVendidos = this.selectedViagem.verifyDisponibility();
            for (String numID : assentosVendidos) {
                Button bt = this.getButtonByCSSId("#btnAssento" + numID);
                bt.setDisable(true);
            }
        }
    }

    private void toggleStateAndVisualOfSits(){
        this.resetSits();
        this.disableUnavailableSits();
        this.checkToggle();
    }

    public void selectViagemForSale(MouseEvent mouseEvent) {
        if(mouseEvent.getClickCount() == 1) {
            this.selectedViagem = this.tableViagens.getSelectionModel().getSelectedItem();
            if (this.selectedViagem != null)
                this.toggleStateAndVisualOfSits();
        }
    }

    private Button getButtonByCSSId(String selector){
        return (Button) this.scene.lookup(selector);
    }

    public void purchaseClickedSit(ActionEvent actionEvent) {
        if (this.selectedViagem != null) {
            Button btn = (Button) actionEvent.getSource();
            System.out.println(btn.getId());
            FinalizacaoVendaLoader janelaFinal = new FinalizacaoVendaLoader();
            janelaFinal.start(this.selectedViagem, this.getClientType());
        }
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

    public void openOnibus(ActionEvent actionEvent){
        OnibusLoader janelaOnibus = new OnibusLoader();
        janelaOnibus.start();

    }

    public void openFunc(ActionEvent actionEvent) {
        FuncionarioLoader janelaFunc = new FuncionarioLoader();
        janelaFunc.start();
    }
}

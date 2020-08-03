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
import view.loader.FuncionarioLoader;
import model.entities.Viagem;
import model.usecases.GerarViagensUC;
import view.loader.LinhaLoader;
import view.loader.PassagemLoader;
import view.loader.OnibusLoader;
import view.loader.TrechoLoader;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Set;

public class VendasController {

    @FXML Button btnAssento01, btnAssento02, btnAssento03, btnAssento04, btnAssento05, btnAssento06,
            btnAssento07, btnAssento08, btnAssento09, btnAssento10, btnAssento11, btnAssento12, btnAssento13,
            btnAssento14, btnAssento15, btnAssento16, btnAssento17, btnAssento18, btnAssento19, btnAssento20,
            btnAssento21, btnAssento22, btnAssento23, btnAssento24, btnAssento25, btnAssento26, btnAssento27,
            btnAssento28, btnAssento29, btnAssento30, btnAssento31, btnAssento32, btnAssento33, btnAssento34,
            btnAssento35, btnAssento36, btnAssento37, btnAssento38, btnAssento39, btnAssento40, btnAssento41,
            btnAssento42, btnAssento43, btnAssento44;
    @FXML Menu menuGerenciar;
    @FXML AnchorPane popupReagendamento;
    @FXML TextField txtFieldOrigem, txtFieldDestino;
    @FXML DatePicker datePickerSaida;
    @FXML MenuItem menuOptPassagens;
    @FXML ToggleGroup clienteEspecial;
    @FXML TableView<Viagem> tableViagens;
    @FXML TableColumn<Viagem, String> colLinha, colHorarioSaida;

    private Scene scene;
    private enum TipoEspecial {NÃO, IDOSO, DEFICIENTE}
    private TipoEspecial clientType = TipoEspecial.NÃO;
    private GerarViagensUC gerarViagensUC;
    private ObservableList<Viagem> tableDataViagens;
    private Viagem selectedViagem;

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
        this.btnAssento03.setDisable(mode);
        this.btnAssento04.setDisable(mode);
        this.disableUnavailableSits();
    }

    public void searchForViagens(ActionEvent actionEvent) {
        Date data = this.LocalDateConverter(this.datePickerSaida.getValue());
        String cidadeOrigem = this.txtFieldOrigem.getText();
        String cidadeDestino = this.txtFieldDestino.getText();
        if (this.checkInputsValues(data, cidadeOrigem, cidadeDestino)) {
            List<Viagem> viagens = this.gerarViagensUC.searchForViagens(data, cidadeOrigem, cidadeDestino);
            this.showResultsToTable(viagens);
        }
    }

    private boolean checkInputsValues(Date data, String cidadeOrigem, String cidadeDestino) {
        return  data != null && cidadeOrigem != null && cidadeDestino != null;
    }

    private Date LocalDateConverter(LocalDate localDate){
        /**
         * Metodo temporário, deve fazer parte do verificador de inputs
         */
        return localDate != null ? java.sql.Date.valueOf(localDate) : null;
    }

    private void showResultsToTable(List<Viagem> viagens){
        this.tableDataViagens.setAll(viagens);
    }

    private void resetSits(){
        String color;
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
        Button btn = (Button) actionEvent.getSource();
        System.out.println(btn.getId());
        //todo compra a partir do assento
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

package controller;

import database.dao.PassagemDAO;
import database.utils.DAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.entities.Passagem;
import model.usecases.ConsultarPassagensVendidasUC;
import model.usecases.DevolverPassagensUC;
import view.util.AlertWindow;
import view.util.DataValidator;
import view.util.mask.MaskedTextField;

import java.util.*;

public class PassagemController {
    @FXML MaskedTextField txtSearchCpf;
    @FXML TextField txtSearchId;
    @FXML TableView<Passagem> tablePassagens;
    @FXML TableColumn<Passagem, String> colNumero, colData, colHorarioSaida,
            colLinha, colOrigem, colDestino, colCpf;
    @FXML Button btnReemicao, btnDevolucao, btnReagendamento;
    @FXML ToggleGroup searchType;

    private Passagem passagemReagendamento;
    private Passagem selectedPassagem;
    private ObservableList<Passagem> tableDataPassagens;
    private String messageHead, messageBody;

    private ConsultarPassagensVendidasUC ucPassagensVendidas;
    private DevolverPassagensUC ucDevolverPassagens;

    public PassagemController() {
        DAO<Passagem, String> passagemDAO = new PassagemDAO();
        this.ucPassagensVendidas = new ConsultarPassagensVendidasUC(passagemDAO);
        this.ucDevolverPassagens = new DevolverPassagensUC(passagemDAO);
    }

    @FXML
    private void initialize() {
        this.bindTableColumnsToSource();
        this.bindDataListToTable();
    }

    private void bindTableColumnsToSource() {
        colNumero.setCellValueFactory(new PropertyValueFactory<>("numPassagem"));
        colCpf.setCellValueFactory(new PropertyValueFactory<>("cpf"));
        colData.setCellValueFactory(new PropertyValueFactory<>("dataViagemStr"));
        colHorarioSaida.setCellValueFactory(new PropertyValueFactory<>("horarioSaida"));
        colLinha.setCellValueFactory(new PropertyValueFactory<>("nomeLinha"));
        colOrigem.setCellValueFactory(new PropertyValueFactory<>("cidadeOrigem"));
        colDestino.setCellValueFactory(new PropertyValueFactory<>("cidadeDestino"));
    }

    private void bindDataListToTable() {
        this.tableDataPassagens = FXCollections.observableArrayList();
        this.tablePassagens.setItems(this.tableDataPassagens);
    }

    public void searchForViagens(ActionEvent actionEvent) {
        boolean choice = this.checkSearchType();
        String searchKey = this.verifyTextField(choice);
        if (searchKey != null)
            if (choice) this.searchByNumber(searchKey);
            else this.searchByCpf(searchKey);
        else this.informErrorSearch(choice);
    }

    private void informErrorSearch(boolean choice){
        this.messageHead = "Parâmetros de pesquisa inválidos ou nulos!";
        this.messageBody = (choice?"Número de passagem" : "CPF").concat(" inválido.");
        AlertWindow.errorAlert(messageBody, messageHead);
    }

    private void showResultsToTable(List<Passagem> passagensEncontradas){
        this.tableDataPassagens.setAll(passagensEncontradas);
    }

    private void toggleOptions(boolean mode) {
        this.btnReemicao.setDisable(mode);
        if (this.checkSearchType()) {
            this.btnDevolucao.setDisable(mode);
            this.btnReagendamento.setDisable(mode);
        }
    }

    private void searchByCpf(String cpf){
        List<Passagem> result = ucPassagensVendidas.searchPassagensByCpf(cpf);
        if (result.size() == 0) this.informEmptyResults();
        this.showResultsToTable(result);
    }

    private void searchByNumber(String number) {
        Passagem result = ucPassagensVendidas.searchPassagensByNumber(number);
        List<Passagem> passagensEncontradas = new ArrayList<>();
        if (result != null)
            passagensEncontradas.add(result);
        else this.informEmptyResults();
        this.showResultsToTable(passagensEncontradas);
    }

    private void informEmptyResults(){
        this.messageHead = "Busca não encontrou nenhum resultado válido!";
        this.messageBody = "Reveja os parâmetros informados.";
        AlertWindow.informationAlerta(messageBody, messageHead);
    }

    private boolean checkSearchType() {
        RadioButton selectedRadioButton = (RadioButton) searchType.getSelectedToggle();
        return selectedRadioButton.getId().equals("rdNumero");
    }

    private String verifyTextField(boolean choice){
        if (choice)
            return DataValidator.checkNumId(this.txtSearchId.getText());
        return DataValidator.cpfVerifier(this.txtSearchCpf.getPlainText());
    }

    public void changeInput(ActionEvent actionEvent) {
        boolean mode = this.checkSearchType();
        this.txtSearchId.setVisible(mode);
        this.txtSearchCpf.setVisible(!mode);
    }

    public void printPassagem(ActionEvent actionEvent) {
    }

    private Date getSystemTime(){
        return Calendar.getInstance().getTime();
    }

    private boolean verifyValidity(){
        return this.getSystemTime().compareTo(this.selectedPassagem.getViagem().getData()) < 0;
    }

    private void informErrorExpiredPassagem(){
        this.messageHead = "Ação Inválida!";
        this.messageBody = "Passagem com data e/ou horário expirados";
        AlertWindow.errorAlert(messageBody, messageHead);
    }

    public void removePassagem(ActionEvent actionEvent) {
        this.setPriceMessage();
        if (this.checkActions()){
            this.ucDevolverPassagens.deletePassagem(this.selectedPassagem);
            this.tableDataPassagens.remove(this.selectedPassagem);
            this.selectedPassagem = null;
            this.toggleOptions(true);
        }
    }

    private boolean checkActions(){
        if (this.verifyValidity()){
            if (AlertWindow.verificationAlert(messageBody, messageHead)) return true;
        }
        else this.informErrorExpiredPassagem();
        return false;
    }

    public void reschedulePassagem(ActionEvent actionEvent) {
        this.setRescheduleMessage();
        if (this.checkActions()){
            this.passagemReagendamento = this.selectedPassagem;
            this.closeWindow();
        }
    }

    private void closeWindow(){
        Stage stage = (Stage) btnReemicao.getScene().getWindow();
        stage.close();
    }

    private void setRescheduleMessage(){
        this.messageHead = "Confirmar Reagendamento?";
        this.messageBody = "A passagem será reagendada a partir da escolha de uma nova venda.";
    }

    private void setPriceMessage(){
        this.messageHead = "Confirmar Devolução?";
        this.messageBody = "Valor a devolver para o cliente: "
                .concat(DataValidator.formatCurrencyView(this.selectedPassagem.getPrecoPago()));
    }

    public void selectPassagem(MouseEvent mouseEvent) {
        if(mouseEvent.getClickCount() == 1) {
            this.selectedPassagem = this.tablePassagens.getSelectionModel().getSelectedItem();
            if (this.selectedPassagem != null) this.toggleOptions(false);
            else this.toggleOptions(true);
        }
    }

    public Passagem getPassagemReagendamento() {
        return passagemReagendamento;
    }

}

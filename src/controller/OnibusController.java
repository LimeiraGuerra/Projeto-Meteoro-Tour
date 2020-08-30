package controller;

import database.dao.OnibusDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import model.entities.Onibus;
import model.usecases.GerenciarOnibusUC;
import view.util.AlertWindow;
import view.util.DataValidator;
import view.util.sharedCodes.MaskedTextField;

public class OnibusController {

    @FXML private TableView<Onibus> tabelaOnibus;
    @FXML private TableColumn<Onibus, String> cRenavam;
    @FXML private TableColumn<Onibus, String> cPlaca;
    @FXML private MaskedTextField txtFieldRenavam;
    @FXML private MaskedTextField txtFieldPlaca;

    private String msgBody;
    private ObservableList<Onibus> onibus = FXCollections.observableArrayList();
    private GerenciarOnibusUC ucOnibus;


    public OnibusController(){
        this.ucOnibus = new GerenciarOnibusUC(new OnibusDAO());
    }

    public void initialize(){
        bind();
    }

    public void bind(){
        cRenavam.setCellValueFactory(new PropertyValueFactory<>("renavam"));
        cPlaca.setCellValueFactory(new PropertyValueFactory<>("formatedPlaca"));
        tabelaOnibus.setItems(loadTable());
    }

    private ObservableList<Onibus> loadTable() {
        refreshTable();
        return onibus;
    }

    private void refreshTable(){
        onibus.clear();
        onibus.addAll(ucOnibus.getListOnibus());
        tabelaOnibus.refresh();
    }

    public void addOnibus(ActionEvent actionEvent) {
        tabelaOnibus.getSelectionModel().select(null);
        txtFieldRenavam.setDisable(false);
        clearTextField();
    }

    public void deleteOnibus(ActionEvent actionEvent) {
        if (getOnibusOfSelectedRow() != null) {
            if (AlertWindow.verificationAlert("Tem certeza que deseja excluir esse onibus?", getOnibusOfSelectedRow().toString())) ucOnibus.deleteOnibus(getOnibusOfSelectedRow());
        }
        refreshTable();
        clearTextField();
        txtFieldRenavam.setDisable(false);
    }

    public void saveOnibus(ActionEvent actionEvent) {
        addOrEditFunc();
        //refreshTable();
        tabelaOnibus.getSelectionModel().select(onibus.size());
    }

    private void addOrEditFunc(){
        Onibus onibus = newOnibus();
        if (verifyTextFields(onibus)){
            if (getIndexOfSelectedRow() >= 0){
                editOnibus(getIndexOfSelectedRow());
            }
            else {
                createOnibus(onibus);
            }
        }else {
            AlertWindow.informationAlerta(msgBody, "Alerta.");
        }
    }

    private void editOnibus(int index) {
        Onibus selectedBus = onibus.get(index);
        if (ifTableNotHaveRenavamOrPlaca(selectedBus)){
            setOnibusByTextFields(selectedBus);
            ucOnibus.updateOnibus(selectedBus);
            AlertWindow.informationAlerta("Ônibus: \n"+selectedBus+"editado com sucesso", "");
            refreshTable();
            clearTextField();
        }else {
            AlertWindow.errorAlert("Renavam ou placa já cadastrados no sistema", "");
        }
        txtFieldRenavam.setDisable(false);
    }

    private boolean ifTableNotHaveRenavamOrPlaca(Onibus bus) {
        for (Onibus o: onibus){
            if (!o.equals(bus) && (o.getRenavam().equals(bus.getRenavam()) || o.getPlaca().equals(bus.getPlaca()))){
                return false;
            }
        }
        return true;
    }

    private void setOnibusByTextFields(Onibus bus){
        bus.setRenavam(txtFieldRenavam.getText());
        bus.setPlaca(txtFieldPlaca.getText());
    }

    public void createOnibus(Onibus bus){
        if (ifTableNotContainsFunc(bus)) {
            ucOnibus.saveOnibus(bus);
            AlertWindow.informationAlerta("Onibus: \n"+bus+" adicionado com sucesso", "");
            refreshTable();
            clearTextField();
        } else {
            AlertWindow.errorAlert("Renavam ou placa já cadastrados no sistema", "");
        }
    }

    private Onibus newOnibus(){
        return new Onibus(DataValidator.txtInputVerifier(txtFieldRenavam.getPlainText()),
                DataValidator.txtInputVerifier(txtFieldPlaca.getPlainText()));
    }

    private boolean ifTableNotContainsFunc(Onibus bus){
        for (Onibus o: onibus){
            if (o.getRenavam().equals(bus.getRenavam()) || o.getPlaca().equals(bus.getPlaca())){
                return false;
            }
        }
        return true;
    }

    public void setTextFieldByClick(MouseEvent mouseEvent){
        setTextField();
    }

    private void setTextField(){
        if (getIndexOfSelectedRow() != -1) {
            Onibus bus = getOnibusOfSelectedRow();
            txtFieldRenavam.setPlainText(bus.getRenavam());
            txtFieldRenavam.setDisable(true);
            txtFieldPlaca.setPlainText(bus.getPlaca());
        }
    }

    private void clearTextField(){
        txtFieldPlaca.clear();
        txtFieldRenavam.clear();
    }

    private boolean verifyTextFields(Onibus bus){
        StringBuilder str = new StringBuilder();
        if (bus.getRenavam() == null) str.append("Campo Renavam inválido. \n");
        if (bus.getPlaca() == null) str.append("Campo Placa inválido. \n");
        msgBody = str.toString();
        return str.length() == 0;
    }

    private Onibus getOnibusOfSelectedRow(){
        return tabelaOnibus.getSelectionModel().getSelectedItem();
    }

    private int getIndexOfSelectedRow(){
        return tabelaOnibus.getSelectionModel().getSelectedIndex();
    }
}

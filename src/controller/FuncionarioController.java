package controller;

import database.dao.FuncionarioDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import model.entities.Funcionario;
import model.usecases.GerenciarFuncionarioUC;
import view.util.DataValidator;

import java.util.Iterator;
import java.util.Optional;

public class FuncionarioController{

    public TextField txtFieldCPF;
    public TextField txtFieldNome;
    public TextField txtFieldCargo;
    public TextField txtFieldRG;
    public TableView<Funcionario> tabelaFunc;
    public TableColumn<Funcionario, String> cCPF;
    public TableColumn<Funcionario, String> cNome;
    public TableColumn<Funcionario, String> cRG;
    public TableColumn<Funcionario, String> cCargo;

    private String msgBody;
    //variaveis validação
    private ObservableList<Funcionario> funcionarios = FXCollections.observableArrayList();
    private GerenciarFuncionarioUC ucFuncionario;

    public FuncionarioController(){
        this.ucFuncionario = new GerenciarFuncionarioUC(FuncionarioDAO.getInstancia());
    }

    public void initialize(){
        bind();
    }

    public void bind(){
        cCPF.setCellValueFactory(new PropertyValueFactory<>("cpf"));
        cNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        cRG.setCellValueFactory(new PropertyValueFactory<>("rg"));
        cCargo.setCellValueFactory(new PropertyValueFactory<>("cargo"));
        tabelaFunc.setItems(loadTable());

    }

    private ObservableList<Funcionario> loadTable() {
        refreshTable();
        return funcionarios;
    }

    private void refreshTable(){
        funcionarios.clear();
        funcionarios.addAll(ucFuncionario.getListFunc());
        tabelaFunc.refresh();
    }

    public void addFunc(ActionEvent actionEvent) {
        tabelaFunc.getSelectionModel().select(null);
        clearTextField();
    }

    private void clearTextField() {
        txtFieldCPF.clear();
        txtFieldNome.clear();
        txtFieldCargo.clear();
        txtFieldRG.clear();
    }

    public void deleteFunc(ActionEvent actionEvent) {
        if (getFuncOfSelectedRow() != null){
            if (verificationAlert()) ucFuncionario.deleteFunc(getFuncOfSelectedRow());
        }
        refreshTable();
        clearTextField();
    }

    private boolean verificationAlert(){
        Alert alert= new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("Tem certeza que deseja excluir "+getFuncOfSelectedRow().getNome()+"?");
        alert.setContentText(getFuncOfSelectedRow().toString());
        Optional<ButtonType> result = alert.showAndWait();
        return result.get() == ButtonType.OK;
    }

    public void saveFunc(ActionEvent actionEvent) {
        addOrEditFunc();
        refreshTable();
        tabelaFunc.getSelectionModel().select(funcionarios.size());
    }

    private Funcionario newFunc() {
        String cpf = DataValidator.cpfVerifier(txtFieldCPF.getText());
        String nome = DataValidator.txtInputVerifier(txtFieldNome.getText());
        String rg = DataValidator.rgVerifier(txtFieldRG.getText());
        String cargo = DataValidator.txtInputVerifier(txtFieldCargo.getText());
        return new Funcionario(cpf, nome, rg, cargo);
    }

    private void addOrEditFunc(){
        if (verifyTextFields()) {
            if (getIndexOfSelectedRow() >= 0) {
                editFunc(getIndexOfSelectedRow());
            } else {
                createFunc();
            }
        } else {
            informationAlert(msgBody);
        }
    }

    private boolean verifyTextFields(){
        StringBuilder str = new StringBuilder();
        if (DataValidator.cpfVerifier(txtFieldCPF.getText())== null) str.append("Campo CPF inválido. \n");
        if (DataValidator.txtInputVerifier(txtFieldNome.getText())== null) str.append("Campo nome inválido. \n");
        if (DataValidator.rgVerifier(txtFieldRG.getText())==null) str.append("Campo RG inválido. \n");
        if (DataValidator.txtInputVerifier(txtFieldCargo.getText())==null) str.append("Campo Cargo inválido. \n");
        msgBody = str.toString();
        return str.length() == 0;
    }

    private void informationAlert(String msg){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("AVISO!");
        alert.setContentText(msg);
        alert.showAndWait();
    }

    private void editFunc(int index) {
        Funcionario selectedFunc = funcionarios.get(index);
        if (ifTableNotHaveCpfORg(selectedFunc)){
            setFuncByTextFields(selectedFunc);
            ucFuncionario.updateFunc(selectedFunc);
            informationAlert("Funcionario editado com sucesso");
            refreshTable();
            clearTextField();
        }else {
            errorAlert("CPF ou RG já cadastrados no sistema");
        }
    }

    private void errorAlert(String msg){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText("ERRO!");
        alert.setContentText(msg);
        alert.showAndWait();
    }

    private void setFuncByTextFields(Funcionario func){
        func.setCpf(txtFieldCPF.getText());
        func.setNome(txtFieldNome.getText());
        func.setRg(txtFieldRG.getText());
        func.setCargo(txtFieldCargo.getText());
    }

    private boolean ifTableNotHaveCpfORg(Funcionario func) {
        for (Funcionario f : funcionarios){
            if (!f.equals(func) &&(f.getCpf().equals(func.getCpf()) || f.getRg().equals(func.getRg()))){
                return false;
            }
        }
        return true;
        //return !(func.getCpf().equals(txtFieldCPF.getText()) || func.getRg().equals(txtFieldRG.getText()));
    }

    private void createFunc(){
        Funcionario func = newFunc();
        if (ifTableNotContainsFunc(func)) {
            //s
            ucFuncionario.saveFunc(func);
            informationAlert("Funcionario adicionado com sucesso");
        } else {
            errorAlert("CPF ou RG já cadastrados no sistema");
        }
    }

    private boolean ifTableNotContainsFunc(Funcionario func){
        for (Funcionario f : funcionarios){
            if (f.getCpf().equals(func.getCpf()) || f.getRg().equals(func.getRg())){
                return false;
            }
        }
        return true;
    }

    public void setTextFieldByClick(MouseEvent mouseEvent) {
        setTextField();
    }

    private void setTextField(){
        Funcionario func = getFuncOfSelectedRow();
        txtFieldCPF.setText(func.getCpf());
        txtFieldNome.setText(func.getNome());
        txtFieldRG.setText(func.getRg());
        txtFieldCargo.setText(func.getCargo());
    }

    private Funcionario getFuncOfSelectedRow() {
        return tabelaFunc.getSelectionModel().getSelectedItem();
    }

    private int getIndexOfSelectedRow(){
        return tabelaFunc.getSelectionModel().getSelectedIndex();
    }
}

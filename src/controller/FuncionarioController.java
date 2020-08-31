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
import view.util.AlertWindow;
import view.util.DataValidator;
import view.util.sharedCodes.MaskedTextField;

public class FuncionarioController{

    public MaskedTextField txtFieldCPF;
    public TextField txtFieldNome;
    public TextField txtFieldCargo;
    public TextField txtFieldRG;
    public TableView<Funcionario> tabelaFunc;
    public TableColumn<Funcionario, String> cCPF;
    public TableColumn<Funcionario, String> cNome;
    public TableColumn<Funcionario, String> cRG;
    public TableColumn<Funcionario, String> cCargo;

    private String msgBody;
    private ObservableList<Funcionario> funcionarios = FXCollections.observableArrayList();
    private GerenciarFuncionarioUC ucFuncionario;

    public FuncionarioController(){
        this.ucFuncionario = new GerenciarFuncionarioUC(new FuncionarioDAO());
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
        txtFieldCPF.setDisable(false);
        clearTextField();
    }

    public void deleteFunc(ActionEvent actionEvent) {
        if (getFuncOfSelectedRow() != null){
            if (AlertWindow.verificationAlert("Tem certeza que deseja excluir "+getFuncOfSelectedRow().getNome()+"?", getFuncOfSelectedRow().toString())) ucFuncionario.deleteFunc(getFuncOfSelectedRow());
        }
        refreshTable();
        clearTextField();
        txtFieldCPF.setDisable(false);
    }

    public void saveFunc(ActionEvent actionEvent) {
        addOrEditFunc();
        //refreshTable();
        tabelaFunc.getSelectionModel().select(funcionarios.size());
    }

    private void addOrEditFunc(){
        Funcionario funcionario = newFunc();
        if (verifyTextFields(funcionario)) {
            if (getIndexOfSelectedRow() >= 0) {
                editFunc(getIndexOfSelectedRow());
            } else {
                createFunc(funcionario);
            }
        } else {
            AlertWindow.informationAlerta(msgBody, "Alerta.");
        }
    }

    private void editFunc(int index) {
        Funcionario selectedFunc = funcionarios.get(index);
        setFuncByTextFields(selectedFunc);
        if (ifTableNotHaveCpfOrRg(selectedFunc)){
            ucFuncionario.updateFunc(selectedFunc);
            AlertWindow.informationAlerta("Funcionario: \n"+selectedFunc +"editado com sucesso", "");
            refreshTable();
            clearTextField();
            txtFieldCPF.setDisable(false);
        }else {
            AlertWindow.errorAlert("RG já cadastrado no sistema", "");
            refreshTable();
            tabelaFunc.getSelectionModel().select(index);
            setTextField();
        }

    }

    private boolean ifTableNotHaveCpfOrRg(Funcionario func) {
        for (Funcionario f : funcionarios){
            if (!f.equals(func) && f.getRg().equals(func.getRg())){
                return false;
            }
        }
        return true;
    }

    private void setFuncByTextFields(Funcionario func){
        func.setNome(txtFieldNome.getText());
        func.setRg(txtFieldRG.getText());
        func.setCargo(txtFieldCargo.getText());
    }

    private void createFunc(Funcionario func){
        if (ifTableNotContainsFunc(func)) {
            ucFuncionario.saveFunc(func);
            AlertWindow.informationAlerta("Funcionario: \n"+ func +"adicionado com sucesso", "Funcionário adicionado");
            refreshTable();
            clearTextField();
        } else {
            AlertWindow.errorAlert("CPF ou RG já cadastrados no sistema", "");
        }
    }

    private Funcionario newFunc() {
        String cpf = DataValidator.cpfVerifier(txtFieldCPF.getPlainText());
        String nome = DataValidator.txtInputVerifier(txtFieldNome.getText());
        String rg = DataValidator.rgVerifier(txtFieldRG.getText());
        String cargo = DataValidator.txtInputVerifier(txtFieldCargo.getText());
        return new Funcionario(cpf, nome, rg, cargo);
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
        if (getIndexOfSelectedRow() != -1) {
            Funcionario func = getFuncOfSelectedRow();
            txtFieldCPF.setPlainText(func.getCpf());
            txtFieldCPF.setDisable(true);
            txtFieldNome.setText(func.getNome());
            txtFieldRG.setText(func.getRg());
            txtFieldCargo.setText(func.getCargo());
        }
    }

    private void clearTextField() {
        txtFieldCPF.clear();
        txtFieldNome.clear();
        txtFieldCargo.clear();
        txtFieldRG.clear();
    }

    private boolean verifyTextFields(Funcionario func){
        StringBuilder str = new StringBuilder();
        if (func.getCpf() == null) str.append("Campo CPF inválido. \n");
        if (func.getNome() == null) str.append("Campo nome inválido. \n");
        if (func.getRg() ==null) str.append("Campo RG inválido. \n");
        if (func.getCargo() ==null) str.append("Campo Cargo inválido. \n");
        msgBody = str.toString();
        return str.length() == 0;
    }

    private Funcionario getFuncOfSelectedRow() {
        return tabelaFunc.getSelectionModel().getSelectedItem();
    }

    private int getIndexOfSelectedRow(){
        return tabelaFunc.getSelectionModel().getSelectedIndex();
    }
}

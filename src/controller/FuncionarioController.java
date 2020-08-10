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

    public void deleteFunc(ActionEvent actionEvent) {
        if (getFuncOfSelectedRow() != null){
            if (AlertWindow.verificationAlert("Tem certeza que deseja excluir "+getFuncOfSelectedRow().getNome()+"?", getFuncOfSelectedRow().toString())) ucFuncionario.deleteFunc(getFuncOfSelectedRow());
        }
        refreshTable();
        clearTextField();
    }

    public void saveFunc(ActionEvent actionEvent) {
        addOrEditFunc();
        //refreshTable();
        tabelaFunc.getSelectionModel().select(funcionarios.size());
    }

    private void addOrEditFunc(){
        int selectedRow = getIndexOfSelectedRow();
        if (verifyTextFields()) {
            if (selectedRow >= 0) {
                editFunc(getIndexOfSelectedRow());
            } else {
                createFunc();
            }
        } else {
            AlertWindow.informationAlerta(msgBody, "Alerta.");
        }
    }

    private void editFunc(int index) {
        Funcionario selectedFunc = funcionarios.get(index);
        if (ifTableNotHaveCpfORg(selectedFunc)){
            setFuncByTextFields(selectedFunc);
            ucFuncionario.updateFunc(selectedFunc);
            AlertWindow.informationAlerta("Funcionario: \n"+selectedFunc +"editado com sucesso", "");
            refreshTable();
            clearTextField();
        }else {
            AlertWindow.errorAlert("CPF ou RG já cadastrados no sistema", "");
        }
    }

    private boolean ifTableNotHaveCpfORg(Funcionario func) {
        for (Funcionario f : funcionarios){
            if (!f.equals(func) &&(f.getCpf().equals(func.getCpf()) || f.getRg().equals(func.getRg()))){
                return false;
            }
        }
        return true;
    }

    private void setFuncByTextFields(Funcionario func){
        func.setCpf(txtFieldCPF.getText());
        func.setNome(txtFieldNome.getText());
        func.setRg(txtFieldRG.getText());
        func.setCargo(txtFieldCargo.getText());
    }

    private void createFunc(){
        Funcionario func = newFunc();
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
        String cpf = DataValidator.cpfVerifier(txtFieldCPF.getText());
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
        Funcionario func = getFuncOfSelectedRow();
        txtFieldCPF.setText(func.getCpf());
        txtFieldNome.setText(func.getNome());
        txtFieldRG.setText(func.getRg());
        txtFieldCargo.setText(func.getCargo());
    }

    private void clearTextField() {
        txtFieldCPF.clear();
        txtFieldNome.clear();
        txtFieldCargo.clear();
        txtFieldRG.clear();
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

    private Funcionario getFuncOfSelectedRow() {
        return tabelaFunc.getSelectionModel().getSelectedItem();
    }

    private int getIndexOfSelectedRow(){
        return tabelaFunc.getSelectionModel().getSelectedIndex();
    }
}

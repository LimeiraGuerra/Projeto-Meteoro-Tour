package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import model.entities.Funcionario;

import java.util.Optional;

public class FuncionarioController {


    public TextField txtFieldCPF;
    public TextField txtFieldNome;
    public TextField txtFieldCargo;
    public TextField txtFieldRG;
    public TableView<Funcionario> tabelaFunc;
    public TableColumn<Funcionario, String> cCPF;
    public TableColumn<Funcionario, String> cNome;
    public TableColumn<Funcionario, String> cRG;
    public TableColumn<Funcionario, String> cCargo;


    private ObservableList<Funcionario> funcionarios = FXCollections.observableArrayList();

    public void initialize(){
        bind();
    }

    public void bind(){
        cCPF.setCellValueFactory(new PropertyValueFactory<>("cpf"));
        cNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        cRG.setCellValueFactory(new PropertyValueFactory<>("rg"));
        cCargo.setCellValueFactory(new PropertyValueFactory<>("cargo"));
        tabelaFunc.setItems(loadTable());
        tabelaFunc.getSelectionModel().select(0);
        setTextField();
    }

    private ObservableList<Funcionario> loadTable() {
        Funcionario f = new Funcionario("12345","Gabriel", "54321", "estagiario senior");
        Funcionario f1 = new Funcionario("6789", "Augusto", "9876", "estagiario senior");
        Funcionario f2 = new Funcionario("1011", "Erika", "1101", "product owner");
        funcionarios.addAll(f,f1,f2);
        return funcionarios;
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
        if (verificationAlert()) funcionarios.remove(getIndexOfSelectedRow());

    }

    private boolean verificationAlert(){
        Alert alert= new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("Tem certeza que deseja excluir esse Funcionario?");

        Optional<ButtonType> result = alert.showAndWait();
        return result.get() == ButtonType.OK;
    }

    public void saveFunc(ActionEvent actionEvent) {
        int indexSelectedBus = getIndexOfSelectedRow();
        System.out.println(getIndexOfSelectedRow());
        //tabelaOnibus.getSelectionModel().select(null);
        if (indexSelectedBus >= 0){
            editFunc(indexSelectedBus);
            informationAlert("Ônibus editado com sucesso");
        }
        else {
            Funcionario func = newFunc();
            funcionarios.add(func);
            informationAlert("Ônibus adicionado com sucesso");
            //lbSalvo.setText("Salvo");
        }
        tabelaFunc.getSelectionModel().select(funcionarios.size());
    }

    private Funcionario newFunc() {
        return new Funcionario(txtFieldCPF.getText(), txtFieldNome.getText(), txtFieldRG.getText(), txtFieldCargo.getText());
    }

    private void informationAlert(String msg){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(msg);
        alert.showAndWait();
    }

    private void editFunc(int index) {
        Funcionario selectedFunc = funcionarios.get(index);
        System.out.println(selectedFunc);
        selectedFunc.setCpf(txtFieldCPF.getText());
        selectedFunc.setNome(txtFieldNome.getText());
        selectedFunc.setRg(txtFieldRG.getText());
        selectedFunc.setCargo(txtFieldCargo.getText());
        funcionarios.set(index,selectedFunc);
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

package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import model.Funcionario;
import model.Onibus;

public class FuncionarioController {


    public TextField txtFieldCPF;
    public TextField txtFieldNome;
    public TextField txtFieldCargo;
    public TextField txtFieldRG;
    public TableView tabelaFunc;
    public TableColumn cCPF;
    public TableColumn cNome;
    public TableColumn cRG;
    public TableColumn cCargo;

    private ObservableList<Funcionario> func= FXCollections.observableArrayList();

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
        func.addAll(f,f1,f2);
        return func;
    }

    public void addFunc(ActionEvent actionEvent) {
    }

    public void deleteFunc(ActionEvent actionEvent) {
    }

    public void saveFunc(ActionEvent actionEvent) {
    }



    public void setTextFieldByClick(MouseEvent mouseEvent) {
        setTextField();
    }

    private void setTextField(){
        Onibus bus = getOnibusOfSelectedRow();
        txtFieldRenavam.setText(bus.getRenavam());
        txtFieldPlaca.setText(bus.getPlaca());
    }
}

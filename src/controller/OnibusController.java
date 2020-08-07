package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import model.entities.Onibus;

import java.util.Optional;

public class OnibusController {

    @FXML private TableView<Onibus> tabelaOnibus;
    @FXML private TableColumn<Onibus, String> cRenavam;
    @FXML private TableColumn<Onibus, String> cPlaca;
    @FXML private TextField txtFieldRenavam;
    @FXML private TextField txtFieldPlaca;
    @FXML private Pane paneOnibus;

    private ObservableList<Onibus> onibus = FXCollections.observableArrayList();

    public void initialize(){
        bind();
    }

    public void bind(){
        cRenavam.setCellValueFactory(new PropertyValueFactory<>("renavam"));
        cPlaca.setCellValueFactory(new PropertyValueFactory<>("placa"));
        tabelaOnibus.setItems(loadTable());
        tabelaOnibus.getSelectionModel().select(0);
        setTextField();
    }

    private ObservableList<Onibus> loadTable() {
        Onibus o = new Onibus("12345","54321");
        Onibus o1 = new Onibus("6789","9876");
        onibus.addAll(o,o1);
        return onibus;
    }

    public void saveOnibus(ActionEvent actionEvent) {
        int indexSelectedBus = getIndexOfSelectedRow();
        System.out.println(getIndexOfSelectedRow());
        //tabelaOnibus.getSelectionModel().select(null);
        if (indexSelectedBus >= 0){
            editOnibus(indexSelectedBus);
            informationAlert("Ônibus editado com sucesso");
        }
        else {
            Onibus bus = newOnibus();
            onibus.add(bus);
            informationAlert("Ônibus adicionado com sucesso");
            //lbSalvo.setText("Salvo");
        }
        tabelaOnibus.getSelectionModel().select(onibus.size());
    }

    private void editOnibus(int index) {
        Onibus selectedBus = onibus.get(index);
        System.out.println(selectedBus);
        selectedBus.setRenavam(txtFieldRenavam.getText());
        selectedBus.setPlaca(txtFieldPlaca.getText());
        onibus.set(index,selectedBus);
    }

    private Onibus buscarOnibusList(String renavam, String placa){
        for (Onibus bus: onibus){
            if (bus.getRenavam().equals(renavam) || bus.getPlaca().equals(placa)) return bus;
        }
        return null;
    }

    private Onibus getOnibusOfSelectedRow(){
        return tabelaOnibus.getSelectionModel().getSelectedItem();
    }

    private int getIndexOfSelectedRow(){
        return tabelaOnibus.getSelectionModel().getSelectedIndex();
    }

    private Onibus newOnibus(){
        return new Onibus(txtFieldRenavam.getText(), txtFieldPlaca.getText());
    }

    private void informationAlert(String msg){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(msg);
        alert.showAndWait();
    }

    public void deleteOnibus(ActionEvent actionEvent) {
        /*Alert alert= new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("Tem certeza que deseja excluir esse ônibus?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            onibus.remove(getIndexOfSelectedRow());
        }*/
        if (getOnibusOfSelectedRow() != null) {
            if (verificationAlert()) onibus.remove(getIndexOfSelectedRow());
        }
        clearTextField();
    }

    private boolean verificationAlert(){
        Alert alert= new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("Tem certeza que deseja excluir esse ônibus?");

        Optional<ButtonType> result = alert.showAndWait();
        return result.get() == ButtonType.OK;
    }

    public void addOnibus(ActionEvent actionEvent) {
        tabelaOnibus.getSelectionModel().select(null);
        clearTextField();
    }

    private void setTextField(){
        Onibus bus = getOnibusOfSelectedRow();
        txtFieldRenavam.setText(bus.getRenavam());
        txtFieldPlaca.setText(bus.getPlaca());
    }

    public void setTextFieldByClick(MouseEvent mouseEvent){
        setTextField();
    }

    private void clearTextField(){
        txtFieldPlaca.clear();
        txtFieldRenavam.clear();
    }
}

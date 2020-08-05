package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import model.entities.Trecho;
import model.usecases.GerenciarTrechoUC;
import view.util.AlertWindow;
import view.util.TextFieldValidator;

public class TrechoController {

    @FXML private TableView<Trecho> tabelaTrecho;
    @FXML private TableColumn<Trecho, String> cOrigem;
    @FXML private TableColumn<Trecho, String> cDestino;
    @FXML private TableColumn<Trecho, Double> cQuilometragem;
    @FXML private TableColumn<Trecho, Double> cValorTotal;
    @FXML private TextField tfOrigem;
    @FXML private TextField tfDestino;
    @FXML private TextField tfValorPassagem;
    @FXML private TextField tfValorSeguro;
    @FXML private TextField tfTempoDuracao;
    @FXML private TextField tfQuilometragem;
    @FXML private TextField tfTaxaEmbarque;
    @FXML private Pane paneTrecho;
    @FXML private Pane paneImg;
    @FXML private Button btDeleteTrecho;
    @FXML private Button btSalvaTrecho;
    @FXML private Label lbValorTotal;

    private ObservableList<Trecho> trechos = FXCollections.observableArrayList();
    private GerenciarTrechoUC ucTrecho = new GerenciarTrechoUC();

    public void initialize(){
        bind();
        setVisiblePaneImg(true);
        setVisibleButtonPane(false);
    }

    public void bind(){
        cDestino.setCellValueFactory(new PropertyValueFactory<>("cidadeDestino"));
        cOrigem.setCellValueFactory(new PropertyValueFactory<>("cidadeOrigem"));
        cQuilometragem.setCellValueFactory(new PropertyValueFactory<>("quilometragem"));
        cValorTotal.setCellValueFactory(new PropertyValueFactory<>("valorTotal"));
        tabelaTrecho.setItems(loadTable());
    }

    private ObservableList<Trecho> loadTable() {
        trechos.addAll(ucTrecho.getListTrechos());
        return trechos;
    }

    private Trecho searchTrechoTable(){
        return tabelaTrecho.getSelectionModel().getSelectedItem();
    }

    private Trecho createTrecho(){
        Trecho trecho = ucTrecho.createTrecho(tfOrigem.getText(), tfDestino.getText(), Double.parseDouble(tfQuilometragem.getText()),
                Integer.parseInt(tfTempoDuracao.getText()), Double.parseDouble(tfValorPassagem.getText()),
                Double.parseDouble(tfTaxaEmbarque.getText()), Double.parseDouble(tfValorSeguro.getText()));

        return trecho;
    }

    private Trecho searchTrechoOrigemDestino(){
        return ucTrecho.searchForOrigemDestino(tfOrigem.getText(), tfDestino.getText());
    }

    private void updateTrecho(){
        Trecho trecho = searchTrechoOrigemDestino();
        ucTrecho.updateTrecho(Double.parseDouble(tfQuilometragem.getText()),Integer.parseInt(tfTempoDuracao.getText()),
                Double.parseDouble(tfValorPassagem.getText()), Double.parseDouble(tfTaxaEmbarque.getText()),Double.parseDouble(tfValorSeguro.getText()), trecho);

        trecho.setValorTotal();
        setValorTotal(trecho.getValorTotal());
    }

    @FXML
    private void saveOrUpdateTrecho(ActionEvent actionEvent) {
        if (searchTrechoOrigemDestino() == null && checkTextField()){

           Trecho trecho = createTrecho();
           trechos.add(trecho);
           AlertWindow.informationAlerta("O trecho: " + trecho.toString() + " foi salvo!", "Trecho adicionado");
        }
        else if (searchTrechoTable() != null && checkTextField()) {
            updateTrecho();
            AlertWindow.informationAlerta("O trecho: " + searchTrechoTable().toString() + " foi editado!", "Trecho editado");

        }
        else{
            AlertWindow.informationAlerta("O trecho não pode ser adicionado\nVerifique se não há esse trecho criado!", "Trecho não adicionado :(");
        }
        cleanFields();
        tabelaTrecho.refresh();
        setVisibleButtonPane(false);
        setVisiblePaneImg(true);
        }

    @FXML
    private void viewCreateTrecho() {
        cleanFields();
        btDeleteTrecho.setVisible(false);
        btSalvaTrecho.setVisible(true);
        setVisiblePaneImg(false);
        setDisableOrigemDestino(false);
        paneTrecho.setVisible(true);
    }


    private boolean trechoNoContainsTrechoLinha(Trecho trecho){
        if (trecho.sizeListTrechoLinha() == 0){
            return true;
        }
        return false;
    }

    @FXML
    private void deleteTrecho(ActionEvent actionEvent) {
       Trecho trecho = searchTrechoTable();
       if (trecho != null){
           if (trechoNoContainsTrechoLinha(trecho)){
               if (AlertWindow.verificationAlert("Deseja excluir o trecho: " + trecho.toString() + " ?", "Exclusão de trecho.")){
                   ucTrecho.deleteTrecho(trecho);
                   trechos.remove(trecho);
                   tabelaTrecho.refresh();
               }
           }else{
               AlertWindow.informationAlerta("Trecho não pode ser excluido pois pertece a uma linha.", "Erro na exclusão do trecho " + trecho.toString());
           }
       }
       setVisibleButtonPane(false);
       setVisiblePaneImg(true);
       cleanFields();
    }


    @FXML
    private void seeTrecho(ActionEvent actionEvent) {
        Trecho t = searchTrechoTable();
        if (t != null){
            Trecho trecho = ucTrecho.searchTrecho(t);
            if (trecho.equals(t)) {
                setFieldsTrecho(trecho);
                setDisableOrigemDestino(true);
                setVisibleButtonPane(true);
                setVisiblePaneImg(false);
            }

        }
    }

    private void setValorTotal(double valor){
        lbValorTotal.setText("O valor total do trecho é: " + valor);
    }


    private boolean checkTextField(){
        return !TextFieldValidator.isDate(tfDestino) && !TextFieldValidator.isDate(tfOrigem) &&
                TextFieldValidator.isDouble(tfQuilometragem) &&
                TextFieldValidator.isDouble(tfTempoDuracao) &&
                TextFieldValidator.isDouble(tfValorPassagem) &&
                TextFieldValidator.isDouble(tfTaxaEmbarque) &&
                TextFieldValidator.isDouble(tfValorSeguro);
    }

    private void setFieldsTrecho(Trecho trecho){
        tfOrigem.setText(trecho.getCidadeOrigem());
        tfDestino.setText(trecho.getCidadeDestino());
        tfQuilometragem.setText(String.valueOf(trecho.getQuilometragem()));
        tfTempoDuracao.setText(String.valueOf(trecho.getTempoDuracao()));
        tfValorPassagem.setText(String.valueOf(trecho.getValorPassagem()));
        tfTaxaEmbarque.setText(String.valueOf(trecho.getTaxaEmbarque()));
        tfValorSeguro.setText(String.valueOf(trecho.getValorSeguro()));
        setValorTotal(trecho.getValorTotal());
    }

    private void cleanFields(){
        tfOrigem.clear();
        tfDestino.clear();
        tfQuilometragem.clear();
        tfTempoDuracao.clear();
        tfValorPassagem.clear();
        tfTaxaEmbarque.clear();
        tfValorSeguro.clear();
        lbValorTotal.setText("");
    }


    private void setDisableOrigemDestino(boolean bool){
        tfOrigem.setDisable(bool);
        tfDestino.setDisable(bool);
    }
    private void setVisibleButtonPane(boolean bool){
        btDeleteTrecho.setVisible(bool);
        btSalvaTrecho.setVisible(bool);
        paneTrecho.setVisible(bool);
    }

    private void setVisiblePaneImg(boolean bool) {
        paneImg.setVisible(bool);
    }
}

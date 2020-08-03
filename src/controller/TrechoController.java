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
    @FXML private Label lbSalvo;

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
    private Trecho createTrecho(){
        Trecho trecho = ucTrecho.createTrecho(tfOrigem.getText(), tfDestino.getText(), Double.parseDouble(tfQuilometragem.getText()),
                Double.parseDouble(tfTempoDuracao.getText()), Double.parseDouble(tfValorPassagem.getText()),
                Double.parseDouble(tfTaxaEmbarque.getText()), Double.parseDouble(tfValorSeguro.getText()));

        return trecho;
    }

    private Trecho searchTrechoOrigemDestino(){
        return ucTrecho.searchForOrigemDestino(tfOrigem.getText(), tfDestino.getText());
    }

    private void updateTrecho(){
        Trecho trecho = searchTrechoOrigemDestino();
        ucTrecho.updateTrecho(Double.parseDouble(tfQuilometragem.getText()),Double.parseDouble(tfTempoDuracao.getText()),
                Double.parseDouble(tfValorPassagem.getText()), Double.parseDouble(tfTaxaEmbarque.getText()),Double.parseDouble(tfValorSeguro.getText()), trecho);

        trecho.setValorTotal();
        setValorTotal(trecho.getValorTotal());
    }

    @FXML
    private void saveOrUpdateTrecho(ActionEvent actionEvent) {
        if (searchTrechoOrigemDestino() == null && checkTextField()){

           Trecho trecho = createTrecho();
           trechos.add(trecho);
           lbSalvo.setText("Salvo");
        }
        if (searchTrechoTable() != null && checkTextField()) {
            updateTrecho();
            lbSalvo.setText("Salvo");

        }
        else{
            lbSalvo.setText("NÃO SALVO");
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

    public void deleteTrecho(ActionEvent actionEvent) {

       Trecho trecho = searchTrechoTable();

       if (trecho != null){
           ucTrecho.deleteTrecho(trecho);
           trechos.remove(trecho);
           tabelaTrecho.refresh();
       }
       setVisibleButtonPane(false);
       setVisiblePaneImg(true);
       cleanFields();
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

    private void setVisiblePaneImg(boolean bool) {
        paneImg.setVisible(bool);
    }


    public boolean checkTextField(){
        return !TextFieldValidator.isDate(tfDestino) && !TextFieldValidator.isDate(tfOrigem) &&
                TextFieldValidator.isDouble(tfQuilometragem) &&
                TextFieldValidator.isDouble(tfTempoDuracao) &&
                TextFieldValidator.isDouble(tfValorPassagem) &&
                TextFieldValidator.isDouble(tfTaxaEmbarque) &&
                TextFieldValidator.isDouble(tfValorSeguro);
    }

    public void setDisableOrigemDestino(boolean bool){
        tfOrigem.setDisable(bool);
        tfDestino.setDisable(bool);
    }
    public void setVisibleButtonPane(boolean bool){
        btDeleteTrecho.setVisible(bool);
        btSalvaTrecho.setVisible(bool);
        paneTrecho.setVisible(bool);
    }
}

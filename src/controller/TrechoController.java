package controller;

import database.dao.TrechoDAO;
import database.dao.TrechoLinhaDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import model.entities.Trecho;
import model.usecases.AutoCompleteUC;
import model.usecases.GerenciarTrechoUC;
import view.util.DataValidator;
import view.util.AlertWindow;
import view.util.sharedCodes.AutoCompleteComboBoxListener;
import view.util.sharedCodes.CurrencyField;
import view.util.sharedCodes.DoubleField;
import view.util.sharedCodes.IntegerField;

public class TrechoController {

    @FXML private TableView<Trecho> tabelaTrecho;
    @FXML private TableColumn<Trecho, String> cOrigem;
    @FXML private TableColumn<Trecho, String> cDestino;
    @FXML private TableColumn<Trecho, String> cQuilometragem;
    @FXML private TableColumn<Trecho, String> cValorTotal;
    @FXML private ComboBox<String> cBoxOrigem;
    @FXML private ComboBox<String> cBoxDestino;
    @FXML private IntegerField tfTempoDuracao;
    @FXML private DoubleField tfQuilometragem;
    @FXML private CurrencyField tfValorPassagem;
    @FXML private CurrencyField tfValorSeguro;
    @FXML private CurrencyField tfTaxaEmbarque;
    @FXML private Pane paneTrecho;
    @FXML private Pane paneImg;
    @FXML private Button btDeleteTrecho;
    @FXML private Button btSalvaTrecho;
    @FXML private Label lbValorTotal;

    private ObservableList<Trecho> trechos = FXCollections.observableArrayList();
    private ObservableList<String> cityNames;
    private GerenciarTrechoUC ucTrecho = new GerenciarTrechoUC(new TrechoDAO(), new TrechoLinhaDAO());
    private AutoCompleteUC autoCompleteUC = new AutoCompleteUC(new TrechoLinhaDAO(), null);

    public void initialize(){
        bind();
        setVisiblePaneImg(true);
        setVisibleButtonPane(false);
        this.setAutoComplete();
    }

    private void setAutoComplete(){
        this.cityNames = FXCollections.observableArrayList();
        this.getCityNames();
        this.setValuesToComboBoxes();
        this.setAutoCompleteListeners();
    }

    private void getCityNames(){
        this.cityNames.setAll(autoCompleteUC.getCityNames());
    }

    private void setAutoCompleteListeners(){
        new AutoCompleteComboBoxListener<>(this.cBoxOrigem);
        new AutoCompleteComboBoxListener<>(this.cBoxDestino);
    }

    private void setValuesToComboBoxes(){
        this.cBoxOrigem.setItems(this.cityNames);
        this.cBoxDestino.setItems(this.cityNames);
    }

    private void bind(){
        cDestino.setCellValueFactory(new PropertyValueFactory<>("cidadeDestino"));
        cOrigem.setCellValueFactory(new PropertyValueFactory<>("cidadeOrigem"));
        cQuilometragem.setCellValueFactory(new PropertyValueFactory<>("formatedQuilometragem"));
        cValorTotal.setCellValueFactory(new PropertyValueFactory<>("formatedValorTotal"));
        tabelaTrecho.setItems(loadTable());
    }

    private ObservableList<Trecho> loadTable() {
        trechos.addAll(ucTrecho.getListTrechos());
        return trechos;
    }

    private Trecho searchTrechoTable(){
        return tabelaTrecho.getSelectionModel().getSelectedItem();
    }

    private Trecho createTrecho(String cidadeOrigem, String cidadeDestino) {
        return ucTrecho.createTrecho(cidadeOrigem, cidadeDestino, tfQuilometragem.getAmount(),
                tfTempoDuracao.getAmount().intValue(), tfValorPassagem.getAmount(),
                tfTaxaEmbarque.getAmount(), tfValorSeguro.getAmount());
    }

    private Trecho searchTrechoOrigemDestino(String cidadeOrigem, String cidadeDestino){
        return ucTrecho.searchForOrigemDestino(cidadeOrigem, cidadeDestino);
    }

    private void updateTrecho(Trecho trecho){
        ucTrecho.atualizaTrecho(tfQuilometragem.getAmount(), tfTempoDuracao.getAmount().intValue(),
                tfValorPassagem.getAmount(), tfTaxaEmbarque.getAmount(),tfValorSeguro.getAmount(), trecho);
        trecho.setValorTotal();
        setValorTotal(trecho.getValorTotal());
    }

    @FXML
    private void saveOrUpdateTrecho(ActionEvent actionEvent) {
        String cidadeOrigem = DataValidator.txtInputVerifier(this.getValueComboBox(this.cBoxOrigem));
        String cidadeDestino = DataValidator.txtInputVerifier(this.getValueComboBox(this.cBoxDestino));
        if (checkTextField(cidadeOrigem, cidadeDestino)) {
            Trecho trecho = searchTrechoOrigemDestino(cidadeOrigem, cidadeDestino);
            if (trecho == null) {
                trecho = createTrecho(cidadeOrigem, cidadeDestino);
                trechos.add(trecho);
                AlertWindow.informationAlerta("O trecho: " + trecho.toString() + " foi salvo!", "Trecho adicionado");
                this.getCityNames();
            }else if (searchTrechoTable() != null) {
                updateTrecho(trecho);
                AlertWindow.informationAlerta("O trecho: " + searchTrechoTable().toString() + " foi editado!", "Trecho editado");

            } else {
                AlertWindow.informationAlerta("O trecho não pode ser adicionado\nVerifique se não há esse trecho criado!", "Trecho não adicionado :(");
            }

            cleanFields();
            trechos.clear();
            trechos.addAll(ucTrecho.getListTrechos());
            tabelaTrecho.refresh();
            setVisibleButtonPane(false);
            setVisiblePaneImg(true);
        }else{
            AlertWindow.errorAlert("Campos inválidos", "Trecho não adicionado");
        }

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

    @FXML
    private void deleteTrecho(ActionEvent actionEvent) {
       Trecho trecho = searchTrechoTable();
       if (trecho != null){
           if (ucTrecho.ContainsTrechoLinha(trecho)){
               if (AlertWindow.verificationAlert("Deseja excluir o trecho: " + trecho.toString() + " ?", "Exclusão de trecho.")){
                   ucTrecho.deleteTrecho(trecho);
                   trechos.remove(trecho);
                   tabelaTrecho.refresh();
               }
           }else{
               AlertWindow.errorAlert("Trecho não pode ser excluido pois pertece a uma linha.", "Erro na exclusão do trecho " + trecho.toString());
           }
       }
       setVisibleButtonPane(false);
       setVisiblePaneImg(true);
       cleanFields();
    }
    
    private void setValorTotal(double valor){
        lbValorTotal.setText("O valor total do trecho é: " + DataValidator.formatCurrencyView(valor));
    }

    private boolean checkTextField(String cidadeOrigem, String cidadeDestino) {
        return cidadeOrigem != null && cidadeDestino != null &&
                !cidadeOrigem.equals(cidadeDestino) &&
                DataValidator.verifyNumeric(tfQuilometragem.getAmount()) &&
                DataValidator.verifyNumeric(tfTempoDuracao.getAmount()) &&
                DataValidator.verifyNumeric(tfValorPassagem.getAmount()) &&
                DataValidator.verifyNumeric(tfTaxaEmbarque.getAmount()) &&
                DataValidator.verifyNumeric(tfValorSeguro.getAmount());
    }

    private void setFieldsTrecho(Trecho trecho){
        cBoxOrigem.setValue(trecho.getCidadeOrigem());
        cBoxDestino.setValue(trecho.getCidadeDestino());
        tfQuilometragem.setAmount(trecho.getQuilometragem());
        tfTempoDuracao.setAmount(trecho.getTempoDuracao());
        tfValorPassagem.setAmount(trecho.getValorPassagem());
        tfTaxaEmbarque.setAmount(trecho.getTaxaEmbarque());
        tfValorSeguro.setAmount(trecho.getValorSeguro());
        setValorTotal(trecho.getValorTotal());
    }

    private void cleanFields(){
        cBoxOrigem.getEditor().setText("");
        cBoxDestino.getEditor().setText("");
        tfQuilometragem.clear();
        tfTempoDuracao.clear();
        tfValorPassagem.clear();
        tfTaxaEmbarque.clear();
        tfValorSeguro.clear();
        lbValorTotal.setText("");
    }
    
    private void setDisableOrigemDestino(boolean bool){
        cBoxOrigem.setDisable(bool);
        cBoxDestino.setDisable(bool);
    }
    private void setVisibleButtonPane(boolean bool){
        btDeleteTrecho.setVisible(bool);
        btSalvaTrecho.setVisible(bool);
        paneTrecho.setVisible(bool);
    }

    private void setVisiblePaneImg(boolean bool) {
        paneImg.setVisible(bool);
    }

    @FXML
    private void seeTrechoByClick(MouseEvent mouseEvent) {
        Trecho t = searchTrechoTable();
        if (t != null){
            setFieldsTrecho(t);
            setDisableOrigemDestino(true);
            setVisibleButtonPane(true);
            setVisiblePaneImg(false);
        }

    }

    private String getValueComboBox(ComboBox<String> comboBox){
        return comboBox.getEditor().getText();
    }

}

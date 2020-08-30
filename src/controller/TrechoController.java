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
import model.usecases.GerenciarTrechoUC;
import view.util.DataValidator;
import view.util.AlertWindow;
import view.util.sharedCodes.CurrencyField;
import view.util.sharedCodes.DoubleField;
import view.util.sharedCodes.IntegerField;

public class TrechoController {

    @FXML private TableView<Trecho> tabelaTrecho;
    @FXML private TableColumn<Trecho, String> cOrigem;
    @FXML private TableColumn<Trecho, String> cDestino;
    @FXML private TableColumn<Trecho, String> cQuilometragem;
    @FXML private TableColumn<Trecho, String> cValorTotal;
    @FXML private TextField tfOrigem;
    @FXML private TextField tfDestino;
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
    private GerenciarTrechoUC ucTrecho = new GerenciarTrechoUC(new TrechoDAO(), new TrechoLinhaDAO());

    public void initialize(){
        bind();
        setVisiblePaneImg(true);
        setVisibleButtonPane(false);
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

    private Trecho createTrecho() {
        return ucTrecho.createTrecho(tfOrigem.getText(), tfDestino.getText(), tfQuilometragem.getAmount(),
                tfTempoDuracao.getAmount().intValue(), tfValorPassagem.getAmount(),
                tfTaxaEmbarque.getAmount(), tfValorSeguro.getAmount());
    }

    private Trecho searchTrechoOrigemDestino(){
        return ucTrecho.searchForOrigemDestino(tfOrigem.getText(), tfDestino.getText());
    }

    private void updateTrecho(){
        Trecho trecho = searchTrechoOrigemDestino();
        ucTrecho.atualizaTrecho(tfQuilometragem.getAmount(), tfTempoDuracao.getAmount().intValue(),
                tfValorPassagem.getAmount(), tfTaxaEmbarque.getAmount(),tfValorSeguro.getAmount(), trecho);
        trecho.setValorTotal();
        setValorTotal(trecho.getValorTotal());
    }

    @FXML
    private void saveOrUpdateTrecho(ActionEvent actionEvent) {
        if (checkTextField()) {
            if (searchTrechoOrigemDestino() == null) {
                Trecho trecho = createTrecho();
                trechos.add(trecho);
                AlertWindow.informationAlerta("O trecho: " + trecho.toString() + " foi salvo!", "Trecho adicionado");
            }else if (searchTrechoTable() != null) {
                updateTrecho();
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
            AlertWindow.errorAlert("Campos vazios", "Trecho não adicionado");
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

    private boolean checkTextField() {
        return !tfDestino.getText().isEmpty() && !tfOrigem.getText().isEmpty() &&
                DataValidator.verifyNumeric(tfQuilometragem.getAmount()) &&
                DataValidator.verifyNumeric(tfTempoDuracao.getAmount()) &&
                DataValidator.verifyNumeric(tfValorPassagem.getAmount()) &&
                DataValidator.verifyNumeric(tfTaxaEmbarque.getAmount()) &&
                DataValidator.verifyNumeric(tfValorSeguro.getAmount());
    }

    private void setFieldsTrecho(Trecho trecho){
        tfOrigem.setText(trecho.getCidadeOrigem());
        tfDestino.setText(trecho.getCidadeDestino());
        tfQuilometragem.setAmount(trecho.getQuilometragem());
        tfTempoDuracao.setAmount(trecho.getTempoDuracao());
        tfValorPassagem.setAmount(trecho.getValorPassagem());
        tfTaxaEmbarque.setAmount(trecho.getTaxaEmbarque());
        tfValorSeguro.setAmount(trecho.getValorSeguro());
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

}

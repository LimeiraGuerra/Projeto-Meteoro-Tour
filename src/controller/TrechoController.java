package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import model.entities.Trecho;
import model.usecases.UCGerenciarTrecho;
import view.util.VerificaInputs;


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
    private UCGerenciarTrecho ucTrecho = UCGerenciarTrecho.getInstancia();

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

    private Trecho buscarTrechoTabela(){
        return tabelaTrecho.getSelectionModel().getSelectedItem();
    }

    private void limpaCampos(){
        tfOrigem.clear();
        tfDestino.clear();
        tfQuilometragem.clear();
        tfTempoDuracao.clear();
        tfValorPassagem.clear();
        tfTaxaEmbarque.clear();
        tfValorSeguro.clear();
        lbValorTotal.setText("");
    }

    @FXML
    private void salvarTrecho(ActionEvent actionEvent) {
        if (ucTrecho.buscarTrechoOrigemDestino(tfOrigem.getText(), tfDestino.getText()) == null && verificaTextField()){

            Trecho t = ucTrecho.criarTrecho(tfOrigem.getText(), tfDestino.getText(), Double.parseDouble(tfQuilometragem.getText()),
                    Double.parseDouble(tfTempoDuracao.getText()), Double.parseDouble(tfValorPassagem.getText()),
                    Double.parseDouble(tfTaxaEmbarque.getText()), Double.parseDouble(tfValorSeguro.getText()));

            trechos.add(t);
            lbSalvo.setText("Salvo");
            }

        else if (buscarTrechoTabela() != null && verificaTextField()) {
            Trecho x = ucTrecho.buscarTrechoOrigemDestino(tfOrigem.getText(), tfDestino.getText());

            ucTrecho.editarTrecho(Double.parseDouble(tfQuilometragem.getText()),Double.parseDouble(tfTempoDuracao.getText()),
                                Double.parseDouble(tfValorPassagem.getText()), Double.parseDouble(tfTaxaEmbarque.getText()),Double.parseDouble(tfValorSeguro.getText()), x);

            x.setValorTotal();
            setValorTotal(x.getValorTotal());
            lbSalvo.setText("Salvo");

        }
        else{
            lbSalvo.setText("NÃO SALVO");
        }
        limpaCampos();
        tabelaTrecho.refresh();
        setVisibleButtonPane(false);
        setVisiblePaneImg(true);
        }

    @FXML
    private void criarTrecho() {
        limpaCampos();
        btDeleteTrecho.setVisible(false);
        btSalvaTrecho.setVisible(true);
        setVisiblePaneImg(false);
        setDisableOrigemDestino(false);
        paneTrecho.setVisible(true);
    }

    public void deleteTrecho(ActionEvent actionEvent) {

       Trecho t = buscarTrechoTabela();

       if (t != null){
           ucTrecho.removeTrecho(t);
           trechos.remove(t);
           tabelaTrecho.refresh();
       }

       setVisibleButtonPane(false);
       setVisiblePaneImg(true);
       limpaCampos();
    }

    @FXML
    private void verTrecho(ActionEvent actionEvent) {
        Trecho t = buscarTrechoTabela();
        if (t != null){
            Trecho x = ucTrecho.buscarTrechoOrigemDestino(t.getCidadeOrigem(), t.getCidadeDestino());
            if (x.equals(t)) {
                tfOrigem.setText(x.getCidadeOrigem());
                tfDestino.setText(x.getCidadeDestino());
                tfQuilometragem.setText(String.valueOf(x.getQuilometragem()));
                tfTempoDuracao.setText(String.valueOf(x.getTempoDuracao()));
                tfValorPassagem.setText(String.valueOf(x.getValorPassagem()));
                tfTaxaEmbarque.setText(String.valueOf(x.getTaxaEmbarque()));
                tfValorSeguro.setText(String.valueOf(x.getValorSeguro()));
                setValorTotal(x.getValorTotal());
                setDisableOrigemDestino(true);
                setVisibleButtonPane(true);
                setVisiblePaneImg(false);
            }

        }
    }

    private void setValorTotal(double valor){
        lbValorTotal.setText("O Valor total do trecho é: " + valor);
    }

    private void setVisiblePaneImg(boolean bool) {
        paneImg.setVisible(bool);
    }


    public boolean verificaTextField(){
        return VerificaInputs.isVazio(tfDestino.getText()) && VerificaInputs.isVazio(tfOrigem.getText()) &&
                VerificaInputs.isDouble(tfQuilometragem.getText()) &&
                VerificaInputs.isDouble(tfTempoDuracao.getText()) &&
                VerificaInputs.isDouble(tfValorPassagem.getText()) &&
                VerificaInputs.isDouble(tfTaxaEmbarque.getText()) &&
                VerificaInputs.isDouble(tfValorSeguro.getText());
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

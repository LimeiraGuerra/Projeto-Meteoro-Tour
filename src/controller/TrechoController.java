package controller;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import model.Trecho;


public class TrechoController {

    @FXML private TableView<Trecho> tabelaTrecho;
    @FXML private TableColumn<Trecho, String> cOrigem;
    @FXML private TableColumn<Trecho, String> cDestino;
    @FXML private TableColumn<Trecho, Double> cQuilometragem;
    @FXML private TableColumn<Trecho, Double> cValorTotal;
    @FXML private TextField tfOrigem;
    @FXML private TextField tfDestino;
    @FXML private Label lbValorTotal;
    @FXML private Label lbSalvo;
    @FXML private TextField tfValorPassagem;
    @FXML private TextField tfValorSeguro;
    @FXML private TextField tfTempoDuracao;
    @FXML private TextField tfQuilometragem;
    @FXML private TextField tfTaxaEmbarque;
    @FXML private Pane paneTrecho;
    @FXML private Pane paneImg;
    @FXML private Button btDeleteTrecho;
    @FXML private Button btSalvaTrecho;

    private ObservableList<Trecho> trechos= FXCollections.observableArrayList();

    public void bind(){
        cDestino.setCellValueFactory(new PropertyValueFactory<>("cidadeDestino"));
        cOrigem.setCellValueFactory(new PropertyValueFactory<>("cidadeOrigem"));
        cQuilometragem.setCellValueFactory(new PropertyValueFactory<>("quilometragem"));
        cValorTotal.setCellValueFactory(new PropertyValueFactory<>("valorTotal"));
        tabelaTrecho.setItems(loadTable());
        setVisibleImgTrecho(true);

    }

    private ObservableList<Trecho> loadTable() {
        Trecho t = new Trecho("São Carlos", "Ibaté", 16.4, 15, 3.65, 0.8, 0.2);
        Trecho t1 = new Trecho("Ibaté", "São Carlos", 16.4, 15, 3.65, 0.8, 0.2);
        trechos.addAll(t1,t);
        return trechos;
    }

    public void initialize(){
        btDeleteTrecho.setVisible(false);
        btSalvaTrecho.setVisible(false);
        paneTrecho.setVisible(false);
        bind();
    }

    public void close(MouseEvent mouseEvent) {
        Platform.exit();
        System.exit(0);
    }
    public Trecho buscarTrechoList(String origem, String destino){
        for (Trecho x: trechos) {
            if (x.getCidadeOrigem().equals(origem) && x.getCidadeDestino().equals(destino)){
                return x;
            }
        }
        return null;
    }

    public Trecho buscarTrechoTabela(){
        return tabelaTrecho.getSelectionModel().getSelectedItem();
    }

    public boolean verificaTextField(){
        return !tfDestino.getText().equals("") && !tfOrigem.getText().equals("") &&
                tfQuilometragem.getText().matches("-?\\d+|\\d+.\\d+") &&
                tfTempoDuracao.getText().matches("-?\\d+|\\d+.\\d+") &&
                tfValorPassagem.getText().matches("-?\\d+|\\d+.\\d+") &&
                tfTaxaEmbarque.getText().matches("-?\\d+|\\d+.\\d+") &&
                tfValorSeguro.getText().matches("-?\\d+|\\d+.\\d+");
    }
    public Trecho criarNovoTrecho(){
        return new Trecho(tfOrigem.getText(), tfDestino.getText(), Double.parseDouble(tfQuilometragem.getText()),
                Double.parseDouble(tfTempoDuracao.getText()), Double.parseDouble(tfValorPassagem.getText()),
                Double.parseDouble(tfTaxaEmbarque.getText()), Double.parseDouble(tfValorSeguro.getText()));
    }

    public Trecho editarTrecho(Trecho x){
        x.setTaxaEmbarque(Double.parseDouble(tfTaxaEmbarque.getText()));
        x.setQuilometragem(Double.parseDouble(tfQuilometragem.getText()));
        x.setTempoDuracao(Double.parseDouble(tfTempoDuracao.getText()));
        x.setValorSeguro(Double.parseDouble(tfValorSeguro.getText()));
        x.setValorPassagem(Double.parseDouble(tfValorPassagem.getText()));
        return x;
    }
    public void setDisableOrigemDestino(boolean bool){
        tfOrigem.setDisable(bool);
        tfDestino.setDisable(bool);
    }
    public void salvarTrecho(ActionEvent actionEvent) {
        if (buscarTrechoList(tfOrigem.getText(), tfDestino.getText()) == null && verificaTextField()){
            Trecho t = criarNovoTrecho();
            trechos.add(t);
            setValorTotal(t.getValorTotal());
            lbSalvo.setText("Salvo");

            }
        else if (buscarTrechoTabela() != null && verificaTextField()) {
            Trecho x = buscarTrechoList(tfOrigem.getText(), tfDestino.getText());
            editarTrecho(x);
            x.setValorTotal();
            setValorTotal(x.getValorTotal());
            lbSalvo.setText("Salvo");

        }
        else{
            lbSalvo.setText("NÃO Salvo");
        }
        limpaCampos();
        tabelaTrecho.refresh();
        btDeleteTrecho.setVisible(false);
        btSalvaTrecho.setVisible(false);
        paneTrecho.setVisible(false);
        setVisibleImgTrecho(true);
        }

    public void deleteTrecho(ActionEvent actionEvent) {

       Trecho t = buscarTrechoTabela();
       if (t != null){
           trechos.remove(t);
           tabelaTrecho.refresh();
       }

       paneTrecho.setVisible(false);
       btDeleteTrecho.setVisible(false);
       btSalvaTrecho.setVisible(false);
       setVisibleImgTrecho(true);
       limpaCampos();
    }

    public void verTrecho(ActionEvent actionEvent) {
        Trecho t = buscarTrechoTabela();
        if (t != null){
            Trecho x = buscarTrechoList(t.getCidadeOrigem(), t.getCidadeDestino());
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
                btDeleteTrecho.setVisible(true);
                btSalvaTrecho.setVisible(true);
                setVisibleImgTrecho(false);
                paneTrecho.setVisible(true);
            }

        }
    }
    public void limpaCampos(){
        tfOrigem.clear();
        tfDestino.clear();
        tfQuilometragem.clear();
        tfTempoDuracao.clear();
        tfValorPassagem.clear();
        tfTaxaEmbarque.clear();
        tfValorSeguro.clear();
        lbValorTotal.setText("");
    }
    public void setValorTotal(double valor){
        lbValorTotal.setText("O Valor total do trecho é: " + valor);
    }

    public void setVisibleImgTrecho(boolean bool) {
        paneImg.setVisible(bool);
    }
    public void criarTrecho() {
        limpaCampos();
        btDeleteTrecho.setVisible(false);
        btSalvaTrecho.setVisible(true);
        setVisibleImgTrecho(false);
        setDisableOrigemDestino(false);
        paneTrecho.setVisible(true);
    }
}

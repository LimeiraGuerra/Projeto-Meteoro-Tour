package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import model.entities.Linha;
import model.entities.Trecho;
import model.entities.TrechoLinha;
import model.usecases.GerenciarLinhaUC;
import model.usecases.GerenciarTrechoUC;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class LinhaController{

    @FXML private TableView<Linha> tabelaLinha;
    @FXML private TableColumn<Linha, String> cNome;
    @FXML private TableColumn<Linha, Double> cQuilometragemLinha;
    @FXML private TableColumn<Linha, Double> cValorLinha;
    @FXML private Pane paneLinhaTrecho;
    @FXML private TextField txtNomeLinha;
    @FXML private TableView<Trecho> tabelaLinhaTrecho;
    @FXML private TableColumn<Trecho, String> cTrechoOrigem;
    @FXML private TableColumn<Trecho, String> cTrechoDestino;
    @FXML private ComboBox cbTrechos;
    @FXML Label lbLinha;
    @FXML Button btSalvarAlteracao;
    @FXML Button btExcluirLinha;
    @FXML Button btAdicionarLinha;
    @FXML Pane paneCriaTrecho;
    @FXML private TextField tfOrigem;
    @FXML private TextField tfDestino;
    @FXML private TextField tfValorPassagem;
    @FXML private TextField tfValorSeguro;
    @FXML private TextField tfTempoDuracao;
    @FXML private TextField tfQuilometragem;
    @FXML private TextField tfTaxaEmbarque;
    @FXML private Pane paneImg;
    @FXML private TextField txtHoraTrecho;
    @FXML private TextField txtMinTrecho;


    private ObservableList<Trecho> trechosListTabela = FXCollections.observableArrayList();
    private ObservableList<Linha> linhasListTabela = FXCollections.observableArrayList();
    private GerenciarTrechoUC ucTrecho = new GerenciarTrechoUC();
    private GerenciarLinhaUC ucLinha = new GerenciarLinhaUC();

    public void initialize() {
        bindLinha();
        setImgVisible();
    }

    private void bindLinha(){
        cNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        cQuilometragemLinha.setCellValueFactory(new PropertyValueFactory<>("quilometragemTotal"));
        cValorLinha.setCellValueFactory(new PropertyValueFactory<>("valorTotalLinha"));
        tabelaLinha.setItems(loadTable());
    }

    private ObservableList loadTable() {
        linhasListTabela.clear();
        linhasListTabela.addAll(ucLinha.getListLinha());
        return linhasListTabela;
    }

    private void bindLinhaTrecho(Linha linha){
        cTrechoOrigem.setCellValueFactory(new PropertyValueFactory<>("cidadeOrigem"));
        cTrechoDestino.setCellValueFactory(new PropertyValueFactory<>("cidadeDestino"));
        tabelaLinhaTrecho.setItems(loadTableLinhaTrecho(linha));
        txtNomeLinha.setText(linha.getNome());
    }

    private ObservableList loadTableLinhaTrecho(Linha linha) {
        trechosListTabela.clear();
        trechosListTabela.addAll(linha.getListTrechos());
        return trechosListTabela;
    }

    private void fillComboBox(List<Trecho> trechos){
        ObservableList<String> trechoLinha = FXCollections.observableArrayList();
        for (Trecho t:trechos) {
            trechoLinha.add(t.getCidadeOrigem() + " - " + t.getCidadeDestino());
        }
        cbTrechos.setItems(trechoLinha);
    }

    private Linha searchLinhaTable(){
        return tabelaLinha.getSelectionModel().getSelectedItem();
    }

    private void cleanTxtNome(){
        txtNomeLinha.clear();
    }

    @FXML
    private void seeLinha(ActionEvent actionEvent) {

        Linha linha = searchLinhaTable();
        if (linha != null){
            bindLinhaTrecho(linha);
            fixVisionPane();
            btAdicionarLinha.setVisible(false);
            loadCombobox();
        }

    }

    private void loadCombobox(){
        if (trechosListTabela.size() == 0){
            fillComboBox(ucTrecho.getListTrechos());
        }
        else{
            Trecho t = trechosListTabela.get(trechosListTabela.size() - 1);
            fillComboBox(updateComboBox(t.getCidadeDestino()));
        }
    }
    @FXML
    private void viewCreateLinha(ActionEvent actionEvent) {
        cleanTxtNome();

        setVisibleButtonTxtLinha(true);
        paneLinhaTrecho.setVisible(false);
    }

    @FXML
    private void deleteTrecho(ActionEvent actionEvent) {
        Linha linha = searchLinhaTable();
        Trecho trecho = tabelaLinhaTrecho.getSelectionModel().getSelectedItem();
        TrechoLinha trechoLinha = linha.getTrechoLinha(trecho);
        if (trecho != null && linha != null){

            linha.removeTrecho(trechoLinha);
            loadTableLinhaTrecho(linha);
        }
        loadCombobox();
    }

    @FXML
    private void deleteLinha(ActionEvent actionEvent) {
        Linha linha = searchLinhaTable();
        if (linha != null){
            ucLinha.deleteLinha(linha);
            updateTableLinha();
            setImgVisible();
        }
    }

    @FXML
    private void saveChange(ActionEvent actionEvent) {

        if (searchLinhaTable() != null){
            ucLinha.updateLinha(txtNomeLinha.getText(), searchLinhaTable());
            loadTableLinhaTrecho(searchLinhaTable());
        }
        updateTableLinha();
        setImgVisible();
    }
    private String[] returnCidades(){
        String origemDestino = (String) cbTrechos.getValue();
        String[] cidades = splitOrigemDestino(origemDestino);
        return cidades;
    }

    private void setImgVisible(){
        paneLinhaTrecho.setVisible(false);
        paneCriaTrecho.setVisible(false);
        setVisibleButtonTxtLinha(false);
        paneImg.setVisible(true);
    }

    @FXML
    private void addTrecho(ActionEvent actionEvent) throws ParseException {
        Linha linha = searchLinhaTable();
        String[] cidades = returnCidades();
        int ordem = calcOrdemLinha();
        Time horario = converteTempo();
        Trecho trecho = ucTrecho.searchForOrigemDestino(cidades[0], cidades[1]);
        TrechoLinha trechoLinha = new TrechoLinha(ordem, horario, trecho, linha);
        ucLinha.addLinha(linha);
        loadTableLinhaTrecho(linha);
        loadCombobox();
    }
    private int calcOrdemLinha(){
        return 0;
    }
    private Time converteTempo() throws ParseException {
       String str =  txtHoraTrecho.getText().trim() + ":" +txtMinTrecho.getText().trim();
       SimpleDateFormat formatador = new SimpleDateFormat("HH:mm");
       Date data = formatador.parse(str);
       Time time = new Time(data.getTime());
       return time;
    }

    private List<Trecho> updateComboBox(String destino){
        List<Trecho> trechosLinha = new ArrayList<>();
        for (Trecho trecho : ucTrecho.getListTrechos()) {
            if (trecho.getCidadeOrigem().equals(destino)){
                trechosLinha.add(trecho);
            }
        }
        return trechosLinha;
    }
    private String[] splitOrigemDestino(String cidade){
        String[] cidades = cidade.split("-");
        cidades[0] = cidades[0].trim();
        cidades[1] = cidades[1].trim();
        return cidades;
    }
    public void updateTableLinha(){
        linhasListTabela.clear();
        linhasListTabela.addAll(ucLinha.getListLinha());
        tabelaLinha.refresh();
    }

    private void setVisibleButtonTxtLinha(boolean bool){
        lbLinha.setVisible(bool);
        btAdicionarLinha.setVisible(bool);
        txtNomeLinha.setVisible(bool);
        paneImg.setVisible(false);
    }

    public void addLinha(ActionEvent actionEvent) {
        if (searchLinhaTable() == null){
            ucLinha.createLinha(txtNomeLinha.getText());
            setVisibleButtonTxtLinha(false);
            updateTableLinha();
            paneImg.setVisible(true);
        }
    }

    public void saveTrecho(ActionEvent actionEvent) {
        ucTrecho.createTrecho(tfOrigem.getText(), tfDestino.getText(), Double.parseDouble(tfQuilometragem.getText()),
                Double.parseDouble(tfTempoDuracao.getText()), Double.parseDouble(tfValorPassagem.getText()),
                Double.parseDouble(tfTaxaEmbarque.getText()), Double.parseDouble(tfValorSeguro.getText()));

        fixVisionPane();

    }

    private void fixVisionPane(){
        paneCriaTrecho.setVisible(false);
        setVisibleButtonTxtLinha(true);
        paneLinhaTrecho.setVisible(true);
        paneImg.setVisible(false);
        loadCombobox();
    }

    @FXML
    private void cancelTrecho(ActionEvent actionEvent) {
        clearFieldsTrecho();
        fixVisionPane();
    }

    private void clearFieldsTrecho(){
        tfOrigem.clear();
        tfDestino.clear();
        tfQuilometragem.clear();
        tfTempoDuracao.clear();
        tfValorPassagem.clear();
        tfTaxaEmbarque.clear();
        tfValorSeguro.clear();
    }

    public void viewRegistrerNewTrecho(ActionEvent actionEvent) {
        paneLinhaTrecho.setVisible(false);
        setVisibleButtonTxtLinha(false);
        paneCriaTrecho.setVisible(true);
        clearFieldsTrecho();
    }
}

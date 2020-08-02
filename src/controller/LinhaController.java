package controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import model.entities.Linha;
import model.entities.Trecho;
import model.usecases.UCGerenciarLinha;
import model.usecases.UCGerenciarTrecho;
import java.util.ArrayList;
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


    private ObservableList<Trecho> trechosListTabela = FXCollections.observableArrayList();
    private ObservableList<Linha> linhasListTabela = FXCollections.observableArrayList();
    private UCGerenciarTrecho ucTrecho = UCGerenciarTrecho.getInstancia();
    private UCGerenciarLinha ucLinha = UCGerenciarLinha.getInstancia();

    public void initialize() {
        bindLinha();
        setVisibleButtonTxtLinha(false);
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
        trechosListTabela.addAll(linha.getTrechos());
        return trechosListTabela;
    }

    private void fillComboBox(List<Trecho> trechos){
        ObservableList<String> trechoLinha = FXCollections.observableArrayList();

        for (Trecho t:trechos) {
            trechoLinha.add(t.getCidadeOrigem() + " - " + t.getCidadeDestino());
        }
        trechoLinha.add("Adicionar um novo trecho?");
        cbTrechos.setItems(trechoLinha);
    }

    private Linha buscaLinhaTabela(){
        return tabelaLinha.getSelectionModel().getSelectedItem();
    }

    private void limpaTxtNome(){
        txtNomeLinha.clear();
    }

    @FXML
    private void verLinha(ActionEvent actionEvent) {

        Linha linha = buscaLinhaTabela();
        if (linha != null){
            paneImg.setVisible(false);
            bindLinhaTrecho(linha);
            paneLinhaTrecho.setVisible(true);
            setVisibleButtonTxtLinha(true);
            btAdicionarLinha.setVisible(false);
            carregaCombobox();
        }

    }

    private void carregaCombobox(){
        if (trechosListTabela.size() == 0){
            fillComboBox(ucTrecho.getListTrechos());
        }
        else{
            Trecho t = trechosListTabela.get(trechosListTabela.size() - 1);
            fillComboBox(atualizarComboBox(t.getCidadeDestino()));
        }
    }
    @FXML
    private void criarLinha(ActionEvent actionEvent) {
        limpaTxtNome();
        paneImg.setVisible(false);
        setVisibleButtonTxtLinha(true);
        paneLinhaTrecho.setVisible(false);
    }

    @FXML
    private void excluirTrecho(ActionEvent actionEvent) {
        Linha linha = buscaLinhaTabela();
        Trecho trecho = tabelaLinhaTrecho.getSelectionModel().getSelectedItem();
        if (trecho != null && linha != null){
            linha.removeTrecho(trecho);
            loadTableLinhaTrecho(linha);
        }
        carregaCombobox();
    }

    @FXML
    private void excluirLinha(ActionEvent actionEvent) {
        Linha linha = buscaLinhaTabela();
        if (linha != null){
            ucLinha.removerLinha(linha);
            paneLinhaTrecho.setVisible(false);
            setVisibleButtonTxtLinha(false);
            atualizarTabelaLinha();
            paneImg.setVisible(true);
        }
    }

    @FXML
    private void salvarAlteracao(ActionEvent actionEvent) {

        if (buscaLinhaTabela() != null){
            ucLinha.editarLinha(txtNomeLinha.getText(), buscaLinhaTabela());
            loadTableLinhaTrecho(buscaLinhaTabela());
        }
        atualizarTabelaLinha();
        paneLinhaTrecho.setVisible(false);
        setVisibleButtonTxtLinha(false);
        paneImg.setVisible(true);
    }

    public void adicionarTrecho(ActionEvent actionEvent) {
        Linha linha = buscaLinhaTabela();
        String origemDestino = (String) cbTrechos.getValue();
        if (origemDestino.equals("Adicionar um novo trecho?")){
            paneLinhaTrecho.setVisible(false);
            setVisibleButtonTxtLinha(false);
            limpaCamposTrecho();
            paneCriaTrecho.setVisible(true);
        }
        else{
            String[] cidades = splitOrigemDestino(origemDestino);
            Trecho trecho = ucTrecho.buscarTrechoOrigemDestino(cidades[0], cidades[1]);
            linha.addTrecho(trecho);
            ucLinha.adicionarLinha(linha);
            loadTableLinhaTrecho(linha);
            carregaCombobox();
        }
    }

    public List<Trecho> atualizarComboBox(String destino){
        List<Trecho> trechosLinha = new ArrayList<>();
        for (Trecho trecho : ucTrecho.getListTrechos()) {
            if (trecho.getCidadeOrigem().equals(destino)){
                trechosLinha.add(trecho);
            }
        }
        return trechosLinha;
    }
    public String[] splitOrigemDestino(String cidade){
        String[] cidades = cidade.split("-");
        cidades[0] = cidades[0].trim();
        cidades[1] = cidades[1].trim();
        return cidades;
    }
    public void atualizarTabelaLinha(){
        linhasListTabela.clear();
        linhasListTabela.addAll(ucLinha.getListLinha());
        tabelaLinha.refresh();
    }

    private void setVisibleButtonTxtLinha(boolean bool){
        lbLinha.setVisible(bool);
        btAdicionarLinha.setVisible(bool);
        txtNomeLinha.setVisible(bool);
    }

    public void adicionarLinha(ActionEvent actionEvent) {
        if (buscaLinhaTabela() == null){
            ucLinha.criarLinha(txtNomeLinha.getText());
            setVisibleButtonTxtLinha(false);
            atualizarTabelaLinha();
            paneImg.setVisible(true);
        }
    }

    public void salvarTrecho(ActionEvent actionEvent) {
        ucTrecho.criarTrecho(tfOrigem.getText(), tfDestino.getText(), Double.parseDouble(tfQuilometragem.getText()),
                Double.parseDouble(tfTempoDuracao.getText()), Double.parseDouble(tfValorPassagem.getText()),
                Double.parseDouble(tfTaxaEmbarque.getText()), Double.parseDouble(tfValorSeguro.getText()));

        paneCriaTrecho.setVisible(false);
        setVisibleButtonTxtLinha(true);
        carregaCombobox();
        paneLinhaTrecho.setVisible(true);

    }

    public void cancelarTrecho(ActionEvent actionEvent) {
        limpaCamposTrecho();
        paneCriaTrecho.setVisible(false);
        setVisibleButtonTxtLinha(true);
        paneLinhaTrecho.setVisible(true);
        carregaCombobox();
    }

    private void limpaCamposTrecho(){
        tfOrigem.clear();
        tfDestino.clear();
        tfQuilometragem.clear();
        tfTempoDuracao.clear();
        tfValorPassagem.clear();
        tfTaxaEmbarque.clear();
        tfValorSeguro.clear();
    }

}

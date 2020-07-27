package controller;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import model.Linha;
import model.Trecho;


import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class LinhaController implements Initializable {
    @FXML TableView tabelaLinha;
    @FXML TableColumn<Linha, String> cNome;
    @FXML TableColumn<Linha, Double> cQuilometragemLinha;
    @FXML TableColumn<Linha, Double> cValorLinha;
    @FXML Pane paneLinhaTrecho;
    @FXML TextField txtNomeLinha;
    @FXML TableView tabelaLinhaTrecho;
    @FXML TableColumn<Trecho, String> cTrechoOrigem;
    @FXML TableColumn<Trecho, String> cTrechoDestino;

    private ObservableList<Trecho> trechosTabela = FXCollections.observableArrayList();
    private ObservableList<Linha> linhasTabela = FXCollections.observableArrayList();
    private List<Trecho> trechos = new ArrayList<>();
    private Linha linha = new Linha(1, "01 - Ibaté - São Carlos ");

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        bindLinha();
    }

    public void bindLinha(){
        cNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        cQuilometragemLinha.setCellValueFactory(new PropertyValueFactory<>("quilometragem"));
        cValorLinha.setCellValueFactory(new PropertyValueFactory<>("valorTotal"));
        cTrechoOrigem.setCellValueFactory(new PropertyValueFactory<>("cidadeOrigem"));
        tabelaLinha.setItems(loadTable());


    }
    public void bindLinhaTrecho(){
        cTrechoOrigem.setCellValueFactory(new PropertyValueFactory<>("cidadeOrigem"));
        cTrechoOrigem.setCellValueFactory(new PropertyValueFactory<>("cidadeDestino"));
        tabelaLinha.setItems(loadTableLinhaTrecho());
    }

    private ObservableList loadTableLinhaTrecho() {
        Trecho t = new Trecho("São Carlos", "Ibaté", 16.4, 15, 3.65, 0.8, 0.2);
        Trecho t1 = new Trecho("Ibaté", "São Carlos", 16.4, 15, 3.65, 0.8, 0.2);
        trechos.add(t);
        trechos.add(t1);
        linha.addTrecho(t);
        linha.addTrecho(t1);
        return trechosTabela;
    }

    private ObservableList loadTable() {
       linhasTabela.add(linha);
        return linhasTabela;
    }

    public void close(MouseEvent mouseEvent) {
        Platform.exit();
        System.exit(0);
    }

    public void verLinha(ActionEvent actionEvent) {
    }

    public void criarLinha(ActionEvent actionEvent) {
    }

    public void excluirTrecho(ActionEvent actionEvent) {
    }

    public void excluirLinha(ActionEvent actionEvent) {
    }

    public void salvarAlteracao(ActionEvent actionEvent) {
    }


}

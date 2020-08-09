package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import model.entities.Linha;
import model.entities.Trecho;
import model.entities.TrechoLinha;
import model.usecases.GerenciarLinhaUC;
import model.usecases.GerenciarTrechoUC;
import model.usecases.GerenciarTrechoLinhaUC;
import view.util.AlertWindow;
import view.util.DataValidator;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

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
    @FXML private ComboBox<Trecho> cbTrechos;
    @FXML private Label lbLinha;
    @FXML private Button btAdicionarLinha;
    @FXML private Pane paneCriaTrecho;
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
    @FXML private TextField txtHoraTrechoEdit;
    @FXML private TextField txtMinTrechoEdit;
    @FXML private Label lbNomeTrecho;
    @FXML private Label lbEdit;
    @FXML private Button btAtualizarHora;



    private ObservableList<Trecho> trechosListTabela = FXCollections.observableArrayList();
    private ObservableList<Linha> linhasListTabela = FXCollections.observableArrayList();
    private GerenciarTrechoUC ucTrecho = new GerenciarTrechoUC();
    private GerenciarLinhaUC ucLinha = new GerenciarLinhaUC();
    private GerenciarTrechoLinhaUC ucTrechoLinha = new GerenciarTrechoLinhaUC();

    public void initialize() {
        bindLinha();
        setImgVisible();
        setVisibleTimeFieldsEdit(false);
    }

    private void bindLinha(){
        cNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        cQuilometragemLinha.setCellValueFactory(new PropertyValueFactory<>("quilometragemTotal"));
        cValorLinha.setCellValueFactory(new PropertyValueFactory<>("valorTotalLinha"));
        tabelaLinha.setItems(loadTable());
    }

    private ObservableList<Linha> loadTable() {
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

    private ObservableList<Trecho> loadTableLinhaTrecho(Linha linha) {
        trechosListTabela.clear();
        trechosListTabela.addAll(linha.getListTrechos());
        return trechosListTabela;
    }

    private void fillComboBox(List<Trecho> trechos){
        ObservableList<Trecho> trechoLinha = FXCollections.observableArrayList();
        trechoLinha.addAll(trechos);
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
            Trecho t = trechosListTabela.get(indexLastTrechoList());
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

        if (trecho != null && trechoLinha != null){
            if (isFirstOrLastTrecho(trecho)){
                if (AlertWindow.verificationAlert("Deseja excluir o trecho: " + trecho.toString() + " ?", "Exclusão do trecho na linha: " + linha.getNome())){
                    ucTrechoLinha.deleteTrechoLinha(trechoLinha);
                    loadTableLinhaTrecho(linha);

                }
            }else{
                AlertWindow.informationAlerta("O trecho não pode ser excluído.\nOs trechos só podem ser excluidos se for o primeiro ou o útimo trecho da linha.", "Trecho não excluido");
            }

        }
        setVisibleTimeFieldsEdit(false);
        sizeTableTrechoLinha(292.0);
        loadCombobox();
    }

    @FXML
    private void deleteLinha(ActionEvent actionEvent) {
        Linha linha = searchLinhaTable();
        if (linha != null){
          if (AlertWindow.verificationAlert("Deseja excluir essa linha?", "Exclusão da linha: " + linha.getNome())){
               ucLinha.deleteLinha(linha);
           }
            updateTableLinha();
            setImgVisible();
        }
    }

    @FXML
    private void saveChange(ActionEvent actionEvent) {

        if (searchLinhaTable() != null){
            ucLinha.updateLinha(searchLinhaTable());
            loadTableLinhaTrecho(searchLinhaTable());
        }
        updateTableLinha();
        setImgVisible();
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
        Trecho trecho = cbTrechos.getSelectionModel().getSelectedItem();
        if (isFieldTrechoHoraSet()){
            if (checkHoraMinuto(txtHoraTrecho, txtMinTrecho)){
                ucTrechoLinha.createTrechoLinha(linha, trecho, returnHora(txtHoraTrecho, txtMinTrecho), calcOrdemLinha());
                loadTableLinhaTrecho(linha);
                loadCombobox();
                atualizaHora();
            }
            else{
                AlertWindow.verificationAlert("Informe hora e minutos válidos\nNo padrão: hh:mm.", "Hora informada inválida!");
            }
        }
        else{
            AlertWindow.verificationAlert("Não foi possível adicionar o trecho na linha\nConfira se os campos de hora e minuto estão preenchidos e um trecho selecionado.", "Trecho não adicionado.");
        }

    }

    private int calcOrdemLinha(){
        System.out.println(trechosListTabela.size());
        return trechosListTabela.size();

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

    @FXML
    private void addLinha(ActionEvent actionEvent) {
        if (ucLinha.searchLinhaNome(txtNomeLinha.getText()) == null){
            ucLinha.createLinha(txtNomeLinha.getText());

        }else{
            AlertWindow.informationAlerta("Já existe uma linha com esse nome", "Linha não adicionada.");
        }
        setVisibleButtonTxtLinha(false);
        updateTableLinha();
        paneImg.setVisible(true);
    }

    @FXML
    private void saveTrecho(ActionEvent actionEvent) {
        if(checkTextField()){
            if (ucTrecho.searchForOrigemDestino(tfOrigem.getText(), tfDestino.getText()) == null){
                ucTrecho.createTrecho(tfOrigem.getText(), tfDestino.getText(), Double.parseDouble(tfQuilometragem.getText()),
                        Integer.parseInt(tfTempoDuracao.getText()), Double.parseDouble(tfValorPassagem.getText()),
                        Double.parseDouble(tfTaxaEmbarque.getText()), Double.parseDouble(tfValorSeguro.getText()));
            }else{
                AlertWindow.informationAlerta("Trecho não pode ser salvo, pois há trecho com o mesmo conjunto de cidade Origem - cidade Destino", "Trecho não adicionado.");
            }
        }else{
            AlertWindow.informationAlerta("Trecho não pode ser salvo, pois havia campos vazios", "Trecho não adicionado.");
        }

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

    @FXML
    private void viewRegistrerNewTrecho(ActionEvent actionEvent) {
        paneLinhaTrecho.setVisible(false);
        setVisibleButtonTxtLinha(false);
        paneCriaTrecho.setVisible(true);
        clearFieldsTrecho();
        setOrigemTextField();
    }

    private boolean checkHoraMinuto(TextField t1, TextField t2){
       return DataValidator.isHora(t1.getText()) && DataValidator.isMinuto(t2.getText());

    }

    private boolean isFirstOrLastTrecho(Trecho trecho){
        return trechosListTabela.indexOf(trecho)  == 0 || trechosListTabela.indexOf(trecho) == indexLastTrechoList();
    }
    private boolean isFieldTrechoHoraSet(){
        return (cbTrechos.getSelectionModel().getSelectedItem() != null) && !txtMinTrecho.getText().isEmpty() && !txtHoraTrecho.getText().isEmpty();
    }
    private void setOrigemTextField(){
        if (indexLastTrechoList() != 0){
            Trecho t = trechosListTabela.get(indexLastTrechoList());
            tfOrigem.setText(t.getCidadeDestino());
        }
    }

    private int indexLastTrechoList(){
        if (trechosListTabela.size() > 0){
            return trechosListTabela.size() - 1;
        }
        return 0;
    }

    private String calculateTimeOfExitTrecho() throws ParseException {
        GregorianCalendar gregorianCalendar = new GregorianCalendar();
        if (trechosListTabela.size() > 0){
            gregorianCalendar.setTime(returnHora(txtHoraTrecho, txtMinTrecho));
            int amount = trechosListTabela.get(indexLastTrechoList()).getTempoDuracao();
            gregorianCalendar.add(Calendar.MINUTE, amount);
            SimpleDateFormat formatador = new SimpleDateFormat("HH:mm");
            return formatador.format(gregorianCalendar.getTime());
        }
       return "";
    }

    private void atualizaHora() throws ParseException{
        String[] str = calculateTimeOfExitTrecho().split(":");
        if (!str[0].equals("")){
            txtHoraTrecho.setText(str[0]);
            txtMinTrecho.setText(str[1]);
        }
    }

    private boolean checkTextField() {
        return !tfDestino.getText().isEmpty() && !tfOrigem.getText().isEmpty() &&
                DataValidator.isDouble(tfQuilometragem.getText()) &&
                DataValidator.isDouble(tfTempoDuracao.getText()) &&
                DataValidator.isDouble(tfValorPassagem.getText()) &&
                DataValidator.isDouble(tfTaxaEmbarque.getText()) &&
                DataValidator.isDouble(tfValorSeguro.getText());
    }

    @FXML
    private void organizeFieldtHoraEdit(MouseEvent mouseEvent) throws ParseException {
        sizeTableTrechoLinha(258.0);
        setVisibleTimeFieldsEdit(true);
        Trecho t = tabelaLinhaTrecho.getSelectionModel().getSelectedItem();
        if (t!=null){
            TrechoLinha trechoL = searchLinhaTable().getTrechoLinha(t);
            setFieldHoraEdit(returnHorario(trechoL), t.toString());
        }
    }

    private String[] returnHorario(TrechoLinha trechoLinha) throws ParseException {
        Date hora = trechoLinha.getHorarioSaida();
        SimpleDateFormat formatador = new SimpleDateFormat("HH:mm");
        String horas = formatador.format(hora);
        String[] horario = horas.split(":");
        return horario;
    }

    private void setFieldHoraEdit(String[] horario, String nomeTrecho){
        txtHoraTrechoEdit.setText(horario[0]);
        txtMinTrechoEdit.setText(horario[1]);
        lbNomeTrecho.setText(nomeTrecho);
    }

    private void sizeTableTrechoLinha(Double tamanho){
        tabelaLinhaTrecho.setPrefHeight(tamanho);
    }

    private void setVisibleTimeFieldsEdit(boolean bool){
        lbNomeTrecho.setVisible(bool);
        txtHoraTrechoEdit.setVisible(bool);
        txtMinTrechoEdit.setVisible(bool);
        lbEdit.setVisible(bool);
        btAtualizarHora.setVisible(bool);
    }

    private Date returnHora(TextField t1, TextField t2) throws ParseException {
        String str =  t1.getText().trim() + ":" + t2.getText().trim();
        SimpleDateFormat formatador = new SimpleDateFormat("HH:mm");
        return formatador.parse(str);
    }

    @FXML
    private void atualizarhorario(ActionEvent actionEvent) throws ParseException {
        Date hora = returnHora(txtHoraTrechoEdit, txtMinTrechoEdit);
        if (checkHoraMinuto(txtHoraTrechoEdit, txtMinTrechoEdit)) {
            Trecho t = tabelaLinhaTrecho.getSelectionModel().getSelectedItem();
            TrechoLinha trechoL = searchLinhaTable().getTrechoLinha(t);
            trechoL.setHorarioSaida(hora);
            setVisibleTimeFieldsEdit(false);
            sizeTableTrechoLinha(292.0);
        }else{
            AlertWindow.informationAlerta("Padrão de hora (hh:mm) não foi seguido.", "Atualização não realizada.");
        }
    }

}


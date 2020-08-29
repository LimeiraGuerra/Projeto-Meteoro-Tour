package controller;

import database.dao.LinhaDAO;
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
import model.entities.Linha;
import model.entities.Trecho;
import model.entities.TrechoLinha;
import model.usecases.GerenciarLinhaUC;
import model.usecases.GerenciarTrechoUC;
import model.usecases.GerenciarTrechoLinhaUC;
import view.util.AlertWindow;
import view.util.DataValidator;
import view.util.sharedCodes.CurrencyField;

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
    @FXML private CurrencyField tfValorPassagem;
    @FXML private CurrencyField tfValorSeguro;
    @FXML private CurrencyField tfTaxaEmbarque;
    @FXML private TextField tfTempoDuracao;
    @FXML private TextField tfQuilometragem;
    @FXML private Pane paneImg;
    @FXML private TextField txtHoraTrecho;
    @FXML private TextField txtMinTrecho;
    @FXML private TextField txtHoraTrechoEdit;
    @FXML private TextField txtMinTrechoEdit;
    @FXML private Label lbNomeTrecho;
    @FXML private Label lbEdit;
    @FXML private Button btAtualizarHora;
    @FXML private Button btExcluirTrecho;
    @FXML private Button btAdicionarTrecho;
    @FXML private Button btCadastrarTrecho;

    private ObservableList<Trecho> trechosListTabela = FXCollections.observableArrayList();
    private ObservableList<Linha> linhasListTabela = FXCollections.observableArrayList();
    private GerenciarTrechoUC ucTrecho = new GerenciarTrechoUC(new TrechoDAO(), new TrechoLinhaDAO());
    private GerenciarLinhaUC ucLinha = new GerenciarLinhaUC(new LinhaDAO(), new TrechoLinhaDAO());
    private GerenciarTrechoLinhaUC ucTrechoLinha = new GerenciarTrechoLinhaUC(new TrechoLinhaDAO());
    private int dPlusHolder = 0;

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

    private void cleanTxt(){
        txtNomeLinha.clear();
        txtMinTrechoEdit.clear();
        txtMinTrecho.clear();
        txtHoraTrechoEdit.clear();
        txtHoraTrecho.clear();
    }

    @FXML
    private void seeLinha(ActionEvent actionEvent) {
        setDisableText(false);
        Linha linha = searchLinhaTable();
        if (linha != null){
            bindLinhaTrecho(linha);
            fixVisionPane();
            btAdicionarLinha.setVisible(false);
            loadCombobox();
            calcDplus();

            if (!ucLinha.checkLinha(linha)){
                AlertWindow.informationAlerta("Essa linha já possui vendas.\nPor isso é impossível alterar seus dados", "Linha não editável");
                setDisableText(true);
            }
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
        setDisableText(false);
        cleanTxt();
        setVisibleButtonTxtLinha(true);
        paneLinhaTrecho.setVisible(false);
    }

    @FXML
    private void deleteTrecho(ActionEvent actionEvent) {
        Linha linha = searchLinhaTable();
        Trecho trecho = tabelaLinhaTrecho.getSelectionModel().getSelectedItem();
        TrechoLinha trechoLinha = linha.getTrechoLinha(trecho);

        if (trecho != null && trechoLinha != null){
            if (isLastTrecho(trecho)){
                if (AlertWindow.verificationAlert("Deseja excluir o trecho: " + trecho.toString() + " ?", "Exclusão do trecho na linha: " + linha.getNome())){
                    ucTrechoLinha.deleteTrechoLinha(trechoLinha);
                    loadTableLinhaTrecho(linha);

                }
            }else{
                AlertWindow.informationAlerta("O trecho não pode ser excluído.\nOs trechos só podem ser excluidos se for o útimo trecho da linha.", "Trecho não excluido");
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
            if (ucLinha.checkLinha(linha)){
                if (AlertWindow.verificationAlert("Deseja excluir essa linha?", "Exclusão da linha: " + linha.getNome())){
                    ucLinha.deleteLinha(linha);
                }
            }else{
                if (AlertWindow.verificationAlert("Essa linha não pode ser excluida, pois já possui vendas.\nDeseja colocar essa linha como INATIVA?", "Linha não deletada")){
                    linha.setInativo(true);
                    ucLinha.updateLinha(linha);
                }
            }
            setDisableText(false);
            updateTableLinha();
            setImgVisible();
        }
    }

    @FXML
    private void saveChange(ActionEvent actionEvent) {
        if (searchLinhaTable() != null){
            if (searchLinhaNome(txtNomeLinha.getText()) == null || searchLinhaTable().getNome().equals(txtNomeLinha.getText())){
                searchLinhaTable().setNome(txtNomeLinha.getText());
                ucLinha.updateLinha(searchLinhaTable());
                loadTableLinhaTrecho(searchLinhaTable());
            }else{
                AlertWindow.informationAlerta("Já existe uma linha com esse nome", "Nome da linha não editado.");
            }

        }
        setDisableText(false);
        updateTableLinha();
        setImgVisible();
    }

    private void setImgVisible(){
        paneLinhaTrecho.setVisible(false);
        paneCriaTrecho.setVisible(false);
        setVisibleButtonTxtLinha(false);
        cleanTxt();
        paneImg.setVisible(true);
    }

    @FXML
    private void addTrecho(ActionEvent actionEvent) throws ParseException {
        setDisableText(false);
        Linha linha = searchLinhaTable();
        Trecho trecho = cbTrechos.getSelectionModel().getSelectedItem();
        if (isFieldTrechoHoraSet()){
            if (checkHoraMinuto(txtHoraTrecho, txtMinTrecho)){
                TrechoLinha trechoL = new TrechoLinha(calcOrdemLinha(), returnHora(txtHoraTrecho, txtMinTrecho), trecho, linha);
                atualizaHora(trechoL);
                trechoL.setdPlus(dPlusHolder);
                ucTrechoLinha.saveTrechoLinha(trechoL);
                loadTableLinhaTrecho(linha);
                loadCombobox();
            }else{
                AlertWindow.errorAlert("Informe hora e minutos válidos\nNo padrão: hh:mm.", "Hora informada inválida!");
            }
        }else{
            AlertWindow.errorAlert("Não foi possível adicionar o trecho na linha\nConfira se os campos de hora e minuto estão preenchidos e um trecho selecionado.", "Trecho não adicionado.");
        }
    }

    private int calcOrdemLinha(){
        return trechosListTabela.size() + 1;
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
        if (searchLinhaNome(txtNomeLinha.getText()) == null){
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
                        Integer.parseInt(tfTempoDuracao.getText()), tfValorPassagem.getAmount(),
                        tfTaxaEmbarque.getAmount(), tfValorSeguro.getAmount());
            }else{
                AlertWindow.informationAlerta("Trecho não pode ser salvo, pois há trecho com o mesmo conjunto de cidade Origem - cidade Destino", "Trecho não adicionado.");
            }
        }else{
            AlertWindow.errorAlert("Trecho não pode ser salvo, pois havia campos vazios", "Trecho não adicionado.");
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

    private boolean isLastTrecho(Trecho trecho){
        return trechosListTabela.indexOf(trecho) == indexLastTrechoList();
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

    private String calculateTimeOfExitTrecho(TrechoLinha tl) throws ParseException {
        GregorianCalendar gregorianCalendar = new GregorianCalendar();
        gregorianCalendar.setTime(returnHora(txtHoraTrecho, txtMinTrecho));
        int amount = tl.getTrecho().getTempoDuracao();
        gregorianCalendar.add(Calendar.MINUTE, amount);
        calcDplus();
        SimpleDateFormat formatador = new SimpleDateFormat("HH:mm");
        return formatador.format(gregorianCalendar.getTime());
    }

    private void calcDplus(){
        if (trechosListTabela.size() > 0){
            GregorianCalendar gc = new GregorianCalendar();
            SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
            gc.setTime(getLastTrechoLinha().getHorarioSaida());
            gc.add(Calendar.MINUTE, trechosListTabela.get(indexLastTrechoList()).getTempoDuracao());
            int dPlus = ft.format(gc.getTime()).compareTo(ft.format(getLastTrechoLinha().getHorarioSaida()));
            dPlusHolder = getLastTrechoLinha().getdPlus() + dPlus;
        }
    }

    private TrechoLinha getLastTrechoLinha(){
        return trechosListTabela.get(indexLastTrechoList()).getTrechoLinha();
    }

    private void atualizaHora(TrechoLinha tl) throws ParseException{
        String[] str = calculateTimeOfExitTrecho(tl).split(":");
        if (!str[0].equals("")){
            txtHoraTrecho.setText(str[0]);
            txtMinTrecho.setText(str[1]);
        }
    }

    private boolean checkTextField() {
        return !tfDestino.getText().isEmpty() && !tfOrigem.getText().isEmpty() &&
                DataValidator.isDouble(tfQuilometragem.getText()) &&
                DataValidator.isDouble(tfTempoDuracao.getText()) &&
                DataValidator.isDouble(tfValorPassagem.getAmount() +"") &&
                DataValidator.isDouble(tfTaxaEmbarque.getAmount() +"") &&
                DataValidator.isDouble(tfValorSeguro.getAmount() +"");
    }

    @FXML
    private void organizeFieldtHoraEdit(MouseEvent mouseEvent) {
        sizeTableTrechoLinha(258.0);
        setVisibleTimeFieldsEdit(true);
        Trecho t = tabelaLinhaTrecho.getSelectionModel().getSelectedItem();
        if (t!=null){
            TrechoLinha trechoL = searchLinhaTable().getTrechoLinha(t);
            setFieldHoraEdit(returnHorario(trechoL), t.toString());
        }
    }

    private String[] returnHorario(TrechoLinha trechoLinha) {
        Date hora = trechoLinha.getHorarioSaida();
        SimpleDateFormat formatador = new SimpleDateFormat("HH:mm");
        String horas = formatador.format(hora);
        return horas.split(":");
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
            ucTrechoLinha.updateTrechoLinha(trechoL);
            setVisibleTimeFieldsEdit(false);
            sizeTableTrechoLinha(292.0);
        }else{
            AlertWindow.errorAlert("Padrão de hora (hh:mm) não foi seguido.", "Atualização não realizada.");
        }
    }

    private Linha searchLinhaNome(String nome){
        for (Linha linha : linhasListTabela) {
            if(linha.getNome().equals(nome)){
                return linha;
            }
        }
        return null;
    }

    private void setDisableText(boolean bool){
        txtHoraTrecho.setDisable(bool);
        txtHoraTrechoEdit.setDisable(bool);
        txtMinTrechoEdit.setDisable(bool);
        txtMinTrecho.setDisable(bool);
        txtNomeLinha.setDisable(bool);
        cbTrechos.setDisable(bool);
        btAtualizarHora.setDisable(bool);
        btExcluirTrecho.setDisable(bool);
        btAdicionarTrecho.setDisable(bool);
        btCadastrarTrecho.setDisable(bool);
    }
}


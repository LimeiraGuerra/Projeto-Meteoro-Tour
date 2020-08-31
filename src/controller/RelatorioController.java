package controller;

import database.dao.InfoRelatorioDAO;
import database.dao.LinhaDAO;
import database.dao.TrechoLinhaDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import model.entities.InfoLinhaTrechoRelatorio;
import model.usecases.AutoCompleteUC;
import model.usecases.EmitirRelatoriosUC;
import view.util.AlertWindow;
import view.util.DataValidator;
import view.util.sharedCodes.AutoCompleteComboBoxListener;
import view.util.sharedCodes.CurrencyField;
import view.util.sharedCodes.IntegerField;
import view.util.sharedCodes.MaskedTextField;
import java.io.File;
import java.util.*;

public class RelatorioController {
    @FXML DatePicker dtPickerIni, dtPickerFim;
    @FXML Label lbTotalUso, lbTotalLucro, lbMediaUso, lbMediaLucro;
    @FXML TableView<InfoLinhaTrechoRelatorio> tableRelatorio;
    @FXML TableColumn<InfoLinhaTrechoRelatorio, String>
            colData, colLinha, colHorarioSaida, colTrecho, colUso, colLucro;
    @FXML Pane paneFilter;
    @FXML Button btnExport;
    @FXML ComboBox<String> cBoxLinha, cBoxTrechoIni, cBoxTrechoFim;
    @FXML TextField txtFieldHorarioIni, txtFieldHorarioFim;
    @FXML IntegerField txtFieldUsoIni, txtFieldUsoFim;
    @FXML CurrencyField txtFieldLucroIni, txtFieldLucroFim;

    private EmitirRelatoriosUC emitirRelatoriosUC;
    private AutoCompleteUC autoCompleteUC;
    private ObservableList<InfoLinhaTrechoRelatorio> tableDataRelatorio;
    private String messageBody;
    private String messageHead;
    private List<InfoLinhaTrechoRelatorio> relatorioData;
    private ObservableList<String> cityNames, lineNames;

    public RelatorioController() {
        this.autoCompleteUC = new AutoCompleteUC(new TrechoLinhaDAO(), new LinhaDAO());
        this.emitirRelatoriosUC = new EmitirRelatoriosUC(new InfoRelatorioDAO());
    }

    @FXML
    private void initialize(){
        this.bindDataListToTable();
        this.bindColumnsToValues();
        this.setAutoComplete();
    }

    private void setAutoComplete(){
        this.cityNames = FXCollections.observableArrayList();
        this.lineNames = FXCollections.observableArrayList();
        this.getCityAndLineNames();
        this.setValuesToComboBoxes();
        this.setAutoCompleteListeners();
    }

    private void getCityAndLineNames(){
        this.cityNames.setAll(autoCompleteUC.getCityNames());
        this.lineNames.setAll(autoCompleteUC.getLineNames());
    }

    private void setAutoCompleteListeners(){
        new AutoCompleteComboBoxListener<>(this.cBoxTrechoIni);
        new AutoCompleteComboBoxListener<>(this.cBoxTrechoFim);
        new AutoCompleteComboBoxListener<>(this.cBoxLinha);
    }

    private void setValuesToComboBoxes(){
        this.cBoxTrechoIni.setItems(this.cityNames);
        this.cBoxTrechoFim.setItems(this.cityNames);
        this.cBoxLinha.setItems(this.lineNames);
    }

    private void bindDataListToTable() {
        this.tableDataRelatorio = FXCollections.observableArrayList();
        this.tableRelatorio.setItems(this.tableDataRelatorio);
    }

    private void bindColumnsToValues(){
        this.colData.setCellValueFactory(new PropertyValueFactory<>("data"));
        this.colLinha.setCellValueFactory(new PropertyValueFactory<>("nomeLinha"));
        this.colHorarioSaida.setCellValueFactory(new PropertyValueFactory<>("horarioSaida"));
        this.colTrecho.setCellValueFactory(new PropertyValueFactory<>("nomeTrecho"));
        this.colUso.setCellValueFactory(new PropertyValueFactory<>("uso"));
        this.colLucro.setCellValueFactory(new PropertyValueFactory<>("lucroFormated"));
    }

    public void searchDateInterval(ActionEvent actionEvent) {
        Date dateIni = DataValidator.LocalDateConverter(this.dtPickerIni.getValue());
        Date dateEnd = DataValidator.LocalDateConverter(this.dtPickerFim.getValue());
        if (this.checkDateValues(dateIni, dateEnd)) {
            this.relatorioData = emitirRelatoriosUC.searchInfoByDateInterval(dateIni, dateEnd);
            this.setInfoToView(this.relatorioData);
        }
        else {
            this.clearTable();
            this.messageHead = "Parâmetros de pesquisa inválidos ou nulos!";
            AlertWindow.errorAlert(this.messageBody,this.messageHead);
        }
    }

    private void setInfoToView(List<InfoLinhaTrechoRelatorio> listData){
        if (listData.size() > 0) {
            this.setMathOfData(listData);
            this.toggleFilter(false);
        }else {
            this.messageHead = "Busca não encontrou nenhum resultado válido!";
            this.messageBody = "Reveja os parâmetros informados.";
            AlertWindow.informationAlerta(this.messageBody,this.messageHead);
            this.toggleFilter(true);
        }
        this.showResultsToTable(listData);
    }

    private void toggleFilter(Boolean mode){
        this.paneFilter.setDisable(mode);
        this.btnExport.setDisable(mode);
    }

    private void setMathOfData(List<InfoLinhaTrechoRelatorio> listData){
        double  lucro = 0.0;
        int uso = 0;
        for (InfoLinhaTrechoRelatorio info : listData){
            lucro += info.getLucro();
            uso += info.getUso();
        }
        if (listData.size() > 0 && lucro > 0.0 && uso > 0) {
            this.lbTotalLucro.setText(DataValidator.formatCurrencyView(lucro));
            this.lbTotalUso.setText(String.valueOf(uso));
            this.lbMediaLucro.setText(DataValidator.formatCurrencyView(lucro / listData.size()));
            this.lbMediaUso.setText(String.valueOf(uso / listData.size()));
        }
        else this.setZeroOfMathFields();
    }

    private void setZeroOfMathFields(){
        this.lbTotalLucro.setText(DataValidator.formatCurrencyView(0.0));
        this.lbTotalUso.setText(String.valueOf(0));
        this.lbMediaLucro.setText(DataValidator.formatCurrencyView(0.0));
        this.lbMediaUso.setText(String.valueOf(0));
    }

    private void clearTable(){
        this.showResultsToTable(new ArrayList<>());
    }

    private boolean checkDateValues(Date ini, Date end){
        if (ini == null || end == null) {
            this.messageBody = "Campos de intervalo nulos.\n";
            return false;
        }else if (ini.compareTo(end) > 0){
            this.messageBody = "Data início maior que a data final.\n";
            return false;
        }return true;
    }

    private void showResultsToTable(List<InfoLinhaTrechoRelatorio> listData){
        this.tableDataRelatorio.setAll(listData);
    }

    //todo -> precisa refatoracao
    public void filterResults(ActionEvent actionEvent) {
        String nomeLinha = this.getValueComboBox(this.cBoxLinha);
        String nomeTrechoIni = this.getValueComboBox(this.cBoxTrechoIni);
        String nomeTrechoFim = this.getValueComboBox(this.cBoxTrechoFim);
        String horaIni = DataValidator.verifyTime(this.txtFieldHorarioIni.getText());
        String horaFim = DataValidator.verifyTime(this.txtFieldHorarioFim.getText());
        int usoIni = this.txtFieldUsoIni.getAmount().intValue();
        int usoFim = this.txtFieldUsoFim.getAmount().intValue();
        double lucroIni = this.txtFieldLucroIni.amountProperty().doubleValue();
        double lucroFim = this.txtFieldLucroFim.amountProperty().doubleValue();
        List<InfoLinhaTrechoRelatorio> filteredListAux = new ArrayList<>();
        List<InfoLinhaTrechoRelatorio> filteredList = new ArrayList<>();
        String linhaAux = "";
        for (InfoLinhaTrechoRelatorio info : this.relatorioData) {
            if (!nomeLinha.isEmpty() && !info.getNomeLinha().equals(nomeLinha)) continue;
            if (!(usoIni == usoFim && usoFim == 0) &&
                    !(info.getUso() >= usoIni && info.getUso() <= usoFim)) continue;
            if (!(lucroIni == lucroFim && lucroFim == 0.0) &&
                    !(info.getLucro() >= lucroIni && info.getLucro() <= lucroFim)) continue;
            if (!horaIni.isEmpty() && !horaFim.isEmpty() &&
                    (info.getHorarioSaida().compareTo(horaIni) < 0 ||
                            info.getHorarioSaida().compareTo(horaFim) > 0)) continue;
            if (!nomeTrechoIni.isEmpty() && !info.getCidadeOrigem().equals(nomeTrechoIni)) {
                if (!linhaAux.equals(info.getNomeLinha())) continue;
            } else linhaAux = info.getNomeLinha();
            filteredListAux.add(info);
        }linhaAux = "";
        Collections.reverse(filteredListAux);
        for (InfoLinhaTrechoRelatorio info : filteredListAux) {
            if (!nomeTrechoFim.isEmpty() && !info.getCidadeDestino().equals(nomeTrechoFim)){
                if (!linhaAux.equals(info.getNomeLinha())) continue;}
            else linhaAux = info.getNomeLinha();
            filteredList.add(info);
        }
        Collections.reverse(filteredList);
        this.showResultsToTable(filteredList);
        this.setMathOfData(filteredList);
    }

    private String getValueComboBox(ComboBox<String> comboBox){
        String str = comboBox.getSelectionModel().getSelectedItem();
        return str != null ? str.trim() : "";
    }

    public void tableExport(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("CSV Files", "*.csv"));
        File file = fileChooser.showSaveDialog(tableRelatorio.getScene().getWindow());
        this.emitirRelatoriosUC.export(file, this.tableRelatorio.getItems());
    }

    public void clearFields(ActionEvent actionEvent) {
        this.cBoxLinha.setValue("");
        this.cBoxTrechoIni.setValue("");
        this.cBoxTrechoFim.setValue("");
        this.txtFieldHorarioIni.clear();
        this.txtFieldHorarioFim.clear();
        this.txtFieldUsoIni.clear();
        this.txtFieldUsoFim.clear();
        this.txtFieldLucroIni.setAmount(0.0);
        this.txtFieldLucroFim.setAmount(0.0);
        this.showResultsToTable(this.relatorioData);
        this.setMathOfData(this.relatorioData);
    }
}

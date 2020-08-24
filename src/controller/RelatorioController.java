package controller;

import database.dao.InfoRelatorioDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import model.entities.InfoLinhaTrechoRelatorio;
import model.usecases.EmitirRelatoriosUC;
import view.util.AlertWindow;
import view.util.DataValidator;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RelatorioController {
    @FXML DatePicker dtPickerIni, dtPickerFim;
    @FXML Label lbTotalUso, lbTotalLucro, lbMediaUso, lbMediaLucro;
    @FXML TableView<InfoLinhaTrechoRelatorio> tableRelatorio;
    @FXML TableColumn<InfoLinhaTrechoRelatorio, String>
            colData, colLinha, colHorarioSaida, colTrecho, colUso, colLucro;
    @FXML Pane paneFilter;
    @FXML Button btnExport;
    @FXML TextField txtFieldLinha, txtFieldTrecho, txtFieldHorarioIni, txtFieldHorarioFim;

    private EmitirRelatoriosUC emitirRelatoriosUC;
    private ObservableList<InfoLinhaTrechoRelatorio> tableDataRelatorio;
    private String messageBody;
    private String messageHead;
    private List<InfoLinhaTrechoRelatorio> relatorioData;
    private List<InfoLinhaTrechoRelatorio> relatorioFilteredData;

    public RelatorioController() {
        this.emitirRelatoriosUC = new EmitirRelatoriosUC(new InfoRelatorioDAO());
    }

    @FXML
    private void initialize(){
        this.bindDataListToTable();
        this.bindColumnsToValues();
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
            this.relatorioData = emitirRelatoriosUC.searchInfoByInterval(dateIni, dateEnd);
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
        Double lucro = 0.0;
        int uso = 0;
        for (InfoLinhaTrechoRelatorio info : listData){
            lucro += info.getLucro();
            uso += info.getUso();
        }
        this.lbTotalLucro.setText(DataValidator.formatCurrencyView(lucro));
        this.lbTotalUso.setText(String.valueOf(uso));
        this.lbMediaLucro.setText(DataValidator.formatCurrencyView(lucro/listData.size()));
        this.lbMediaUso.setText(String.valueOf(uso/listData.size()));
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

    public void filterResults(ActionEvent actionEvent) {
        String nomeLinha = this.txtFieldLinha.getText().trim();
        String nomeTrecho = this.txtFieldTrecho.getText().trim();
        String horaIni = DataValidator.verifyTime(this.txtFieldHorarioIni.getText());
        String horaFim = DataValidator.verifyTime(this.txtFieldHorarioFim.getText());
        List<InfoLinhaTrechoRelatorio> filteredList = new ArrayList<>();
        for (InfoLinhaTrechoRelatorio info : this.relatorioData){
            if (!nomeLinha.isEmpty() && !info.getNomeLinha().contains(nomeLinha)) continue;
            if (!nomeTrecho.isEmpty() && !info.getNomeTrecho().contains(nomeTrecho)) continue;
            if (!horaIni.isEmpty() && !horaFim.isEmpty() &&
                    (info.getHorarioSaida().compareTo(horaIni) < 0 ||
                    info.getHorarioSaida().compareTo(horaFim) > 0)) continue;
            filteredList.add(info);
        }
        this.showResultsToTable(filteredList);
    }

    public void tableExport(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("CSV Files", "*.csv"));
        File file = fileChooser.showSaveDialog(tableRelatorio.getScene().getWindow());

        if(file != null)
            this.emitirRelatoriosUC.exportToCsv(this.tableRelatorio.getItems(), file.getAbsolutePath());
    }
}

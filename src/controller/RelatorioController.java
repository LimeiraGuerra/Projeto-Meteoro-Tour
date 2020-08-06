package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import model.entities.InfoLinhaTrechoRelatorio;

public class RelatorioController {
    @FXML DatePicker dtPickerIni, dtPickerFim;
    @FXML Label lbTotalUso, lbTotalLucro, lbMediaUso, lbMediaLucro;
    @FXML TableView<InfoLinhaTrechoRelatorio> tableRelatorio;
    @FXML TableColumn<InfoLinhaTrechoRelatorio, String>
            colData, colLinha, colHorarioSaida, colTrecho, colUso, colLucro;

    public void searchDateInterval(ActionEvent actionEvent) {
    }
}

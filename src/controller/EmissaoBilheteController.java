package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.print.Printer;
import javafx.print.PrinterJob;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.entities.Passagem;
import view.util.ConverterPrinterName;
import view.util.DataValidator;

public class EmissaoBilheteController {
    @FXML Label lbPrintState, lbNumPassagem, lbNomeLinha, lbDataVenda, lbOrigem,
            lbDestino, lbDataViagem, lbHorarioSaida, lbAssento, lbValorViagem,
            lbSeguro, lbValorPago, lbNomeCliente, lbCpf, lbRg, lbTelefone;
    @FXML ComboBox<Printer> cBoxPrinters;
    @FXML Button btnPrint;
    @FXML Pane paneBilhete;

    private Passagem passagem;
    private ObservableList<Printer> printersList;
    private PrinterJob printerJob;

    @FXML
    public void initialize(){
        this.setPrintersListToCheckButton();
        this.getConnectedPrinters();
        this.startPrinterJob();
        this.setDefaultPrinter();
    }

    private void setPrintersListToCheckButton(){
        this.printersList = FXCollections.observableArrayList();
        this.cBoxPrinters.setItems(this.printersList);
        this.cBoxPrinters.setConverter(new ConverterPrinterName());
    }

    private void getConnectedPrinters(){
        this.printersList.addAll(Printer.getAllPrinters());
    }

    private void startPrinterJob(){
        this.printerJob = PrinterJob.createPrinterJob();
        this.setPrintStatus();
    }

    private void setDefaultPrinter(){
        Printer printer = Printer.getDefaultPrinter();
        if (printer != null) {
            this.cBoxPrinters.setValue(printer);
            this.printerJob.setPrinter(printer);
        }
    }

    private void setInfoToView(){
        this.lbNumPassagem.setText(String.format("%08d", this.passagem.getNumPassagem()));
        this.lbNomeLinha.setText(this.passagem.getNomeLinha().toUpperCase());
        this.lbDataVenda.setText(this.passagem.getDataVendaStr());
        this.lbOrigem.setText(this.passagem.getCidadeOrigem().toUpperCase());
        this.lbDestino.setText(this.passagem.getCidadeDestino().toUpperCase());
        this.lbDataViagem.setText(this.passagem.getDataViagemStr());
        this.lbHorarioSaida.setText(this.passagem.getHorarioSaida());
        this.lbAssento.setText(this.passagem.getAssentoId());
        this.lbValorViagem.setText(DataValidator.formatCurrencyView(this.passagem.getViagem().getValueViagem()));
        this.lbSeguro.setText(DataValidator.formatCurrencyView(this.passagem.getPaidSeguro()));
        this.lbValorPago.setText(DataValidator.formatCurrencyView(this.passagem.getPrecoPago()));
        this.lbNomeCliente.setText(this.passagem.getNome().toUpperCase());
        this.lbCpf.setText(this.passagem.getFormatedCpf());
        this.lbRg.setText(this.passagem.getRg().toUpperCase());
        this.lbTelefone.setText(this.passagem.getFormatedTelefone());
    }

    public void initPrint(ActionEvent actionEvent) {
        if (this.printerJob != null && this.printerJob.getPrinter() != null){
            if (this.printerJob.printPage(this.paneBilhete)) {
                this.setPrintStatus();
                if (this.printerJob.endJob()) {
                    this.setPrintStatus();
                    this.closeWindow();
                }
            }
        }
    }

    private void closeWindow(){
        Stage stage = (Stage) this.paneBilhete.getScene().getWindow();
        stage.close();
    }

    public void configPage(ActionEvent actionEvent) {
        if (this.printerJob != null && this.printerJob.getPrinter() != null)
            this.printerConfig();
    }

    private void printerConfig() {
        this.printerJob.showPageSetupDialog(this.paneBilhete.getScene().getWindow());
    }

    public void selectPrinter(ActionEvent actionEvent) {
        Printer printer = cBoxPrinters.getSelectionModel().getSelectedItem();
        if (printer != null)
            this.printerJob.setPrinter(printer);
    }

    private void setPrintStatus(){
        switch (this.printerJob.getJobStatus()){
            case NOT_STARTED: this.lbPrintState.setText("Não iniciado"); break;
            case PRINTING: this.lbPrintState.setText("Imprimindo..."); break;
            case DONE: this.lbPrintState.setText("Concluído"); break;
            case ERROR: this.lbPrintState.setText("Erro!"); break;
            case CANCELED: this.lbPrintState.setText("Cancelado"); break;
        }
    }

    public Passagem getPassagem() {
        return passagem;
    }

    public void setPassagem(Passagem passagem) {
        this.passagem = passagem;
        this.setInfoToView();
    }
}

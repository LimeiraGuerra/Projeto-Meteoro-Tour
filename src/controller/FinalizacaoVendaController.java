package controller;

import database.dao.PassagemDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.entities.Passagem;
import model.entities.Viagem;
import model.usecases.DevolverPassagensUC;
import model.usecases.VenderPassagensUC;
import view.loader.EmissaoBilheteLoader;
import view.util.AlertWindow;
import view.util.DataValidator;
import view.util.TipoEspecial;
import view.util.mask.MaskedTextField;

import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;

public class FinalizacaoVendaController {
    @FXML Label lbLinha, lbCidadeOrigem, lbCidadeDestino, lbHorarioSaida, lbValorViagem, lbValorSeguro;
    @FXML ToggleGroup optSeguro;
    @FXML TextField txfFieldNome, txtFieldRg;
    @FXML MaskedTextField txtFieldCpf, txtFieldTelefone;

    private Viagem chosenViagem;
    private TipoEspecial clientType;
    private String chosenSitId;
    private boolean insurance = false, soldSuccess = false;
    private VenderPassagensUC venderPassagensUC;
    private DevolverPassagensUC devolverPassagensUC;
    private String messageBody;
    private double valueViagem, valueSeguro, totalToPay, valueDiscount;
    private Passagem passagemR;

    public FinalizacaoVendaController(){
        this.devolverPassagensUC = new DevolverPassagensUC(new PassagemDAO());
        this.venderPassagensUC = new VenderPassagensUC(new PassagemDAO());
    }

    private void setInfoToView(){
        this.getPrices();
        this.lbLinha.setText(this.chosenViagem.getLinhaName());
        this.lbCidadeOrigem.setText(this.chosenViagem.getCidadeOrigem());
        this.lbCidadeDestino.setText(this.chosenViagem.getCidadeDestino());
        this.lbHorarioSaida.setText(this.chosenViagem.getHorarioSaida());
        this.lbValorViagem.setText(DataValidator.formatCurrencyView(this.valueViagem));
        this.lbValorSeguro.setText(DataValidator.formatCurrencyView(this.valueSeguro));
    }

    public void finalizeSale(ActionEvent actionEvent) throws SQLException {
        this.setTotalToPay();
        Passagem p = this.getClientDataFromView();
        if (p == null) AlertWindow.errorAlert(this.messageBody, "Parâmetros de pesquisa inválidos ou nulos!");
        else {
            this.setPayMessage();
            if (AlertWindow.verificationAlert(this.messageBody, "Confirmar venda?")) {
                this.addInfoToPassagem(p);
                this.venderPassagensUC.saveSale(p);
                this.deleteOldPassagem();
                this.soldSuccess = true;
                this.printPassagem(p);
                this.closeWindow();
            }
        }
    }

    private void printPassagem(Passagem passagem) {
        EmissaoBilheteLoader janelaBilhete = new EmissaoBilheteLoader();
        janelaBilhete.start(passagem);
    }

    private void deleteOldPassagem(){
        if (this.passagemR != null) this.devolverPassagensUC.deletePassagem(this.passagemR);
    }

    private void addInfoToPassagem(Passagem passagem){
        passagem.setPrecoPago(this.totalToPay);
        passagem.setDiscount(this.valueDiscount);
        passagem.setAssentoId(this.chosenSitId);
        passagem.setViagem(this.chosenViagem);
        passagem.setSeguro(this.insurance);
        passagem.setDataCompra(this.getSystemTime());
    }

    private Date getSystemTime(){
        return Calendar.getInstance().getTime();
    }

    private void getPrices(){
        this.valueViagem = Math.round(this.chosenViagem.getValueViagem()*100.0)/100.0;
        this.valueSeguro = Math.round(this.chosenViagem.getValueSeguroViagem()*100.0)/100.0;
    }

    private Passagem getClientDataFromView(){
        Passagem passagem = null;
        String nome = DataValidator.txtInputVerifier(this.txfFieldNome.getText());
        String cpf = DataValidator.cpfVerifier(this.txtFieldCpf.getPlainText());
        String rg = DataValidator.rgVerifier(this.txtFieldRg.getText());
        String telefone = DataValidator.phoneVerifier(this.txtFieldTelefone.getPlainText());
        if (this.checkInputsValues(nome, cpf, rg, telefone))
            passagem = new Passagem(nome, cpf, rg, telefone);
        return passagem;
    }

    private boolean checkInputsValues(String nome, String cpf, String rg, String telefone){
        StringBuilder st = new StringBuilder();
        if(nome == null){st.append("Campo nome inválido.\n");}
        if(cpf == null){st.append("Campo cpf inválido.\n");}
        if(rg == null){st.append("Campo rg inválido.\n");}
        if(telefone == null){st.append("Campo telefone inválido.\n");}
        this.messageBody = st.toString();
        return st.length() == 0;
    }

    private void checkInsuranceRadio(){
        RadioButton selectedRadioButton = (RadioButton) optSeguro.getSelectedToggle();
        this.insurance =  selectedRadioButton.getText().equals("Sim") ? true : false;
    }

    private void setTotalToPay(){
        this.checkInsuranceRadio();
        this.valueDiscount = this.getDiscount();
        this.totalToPay = this.valueViagem * this.valueDiscount;
        if(this.insurance)
            this.totalToPay += this.valueSeguro;
        //if (this.passagemR != null) this.totalToPay -= passagemR.getPrecoPago();
    }

    private void setPayMessage(){
        Double paid = this.passagemR != null? passagemR.getPrecoPago() : 0.0;
        if (this.totalToPay - paid >= 0.00) this.messageBody = "Valor a pagar: ";
        else this.messageBody = "Valor a devolver para o cliente: ";
        this.messageBody += DataValidator.formatCurrencyView(Math.abs(this.totalToPay - paid));
    }

    private double getDiscount(){
        if (this.chosenSitId.equals("03") || this.chosenSitId.equals("04") ||
                this.clientType.equals(TipoEspecial.DEFICIENTE))
            return 0.0;
        else if (this.clientType.equals(TipoEspecial.IDOSO))
            return 0.5;
        return 1.0;
    }

    private void closeWindow(){
        Stage stage = (Stage) lbLinha.getScene().getWindow();
        stage.close();
    }

    public Viagem getChosenViagem() {
        return chosenViagem;
    }

    public void setChosenViagem(Viagem chosenViagem) {
        this.chosenViagem = chosenViagem;
        this.setInfoToView();
    }

    public TipoEspecial getClientType() {
        return clientType;
    }

    public void setClientType(TipoEspecial clientType) {
        this.clientType = clientType;
    }

    public boolean isSoldSuccess() {
        return soldSuccess;
    }

    public void setChosenSitId(String chosenSitId) {
        this.chosenSitId = chosenSitId;
    }

    public void setPassagemR(Passagem passagemR) {
        this.passagemR = passagemR;
    }
}

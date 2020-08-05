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
import view.util.DataValidator;
import view.util.TipoEspecial;

import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

public class FinalizacaoVendaController {
    @FXML Label lbLinha, lbCidadeOrigem, lbCidadeDestino, lbHorarioSaida, lbValorViagem, lbValorSeguro;
    @FXML ToggleGroup optSeguro;
    @FXML TextField txfFieldNome, txtFieldCpf, txtFieldRg, txtFieldTelefone;

    private Viagem chosenViagem;
    private TipoEspecial clientType;
    private String chosenSitId;
    private boolean insurance = false, soldSuccess = false;
    private VenderPassagensUC venderPassagensUC;
    private DevolverPassagensUC devolverPassagensUC;
    private String messageBody;
    private double valueViagem, valueSeguro, totalToPay;
    private Passagem passagemR;

    public FinalizacaoVendaController(){
        this.devolverPassagensUC = new DevolverPassagensUC(PassagemDAO.getInstancia());
        this.venderPassagensUC = new VenderPassagensUC(PassagemDAO.getInstancia());
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

    public void finalizeSale(ActionEvent actionEvent) {
        this.setTotalToPay();
        Passagem p = this.getClientDataFromView();
        if (p == null) this.errorAlert();
        else {
            this.setPayMessage();
            if (this.verificationAlert()) {
                this.addDataToPassagem(p);
                this.venderPassagensUC.saveSale(p);
                this.deleteOldPassagem();
                this.soldSuccess = true;
                this.closeWindow();
            }
        }
    }

    private void deleteOldPassagem(){
        if (this.passagemR != null) this.devolverPassagensUC.deletePassagem(this.passagemR);
    }

    private void addDataToPassagem(Passagem passagem){
        passagem.setPrecoPago(this.totalToPay);
        passagem.setViagem(this.chosenViagem);
        passagem.setDataViagem(this.chosenViagem.getData());
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
        String cpf = DataValidator.cpfVerifier(this.txtFieldCpf.getText());
        String rg = this.txtFieldRg.getText();
        String telefone = this.txtFieldTelefone.getText();
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
        this.totalToPay = this.valueViagem * getDiscount();
        if(this.insurance)
            this.totalToPay += this.valueSeguro;
        if (this.passagemR != null) this.totalToPay -= passagemR.getPrecoPago();
    }

    private void setPayMessage(){
        if (this.totalToPay >= 0.00) this.messageBody = "Valor a pagar: ";
        else this.messageBody = "Valor a devolver para o cliente: ";
        this.messageBody += DataValidator.formatCurrencyView(Math.abs(this.totalToPay));
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

    private boolean verificationAlert(){
        Alert alert= new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("Confirmar venda?");
        alert.setContentText(this.messageBody);
        Optional<ButtonType> result = alert.showAndWait();
        return result.get() == ButtonType.OK;
    }

    private void errorAlert(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erro!");
        alert.setHeaderText("Parâmetros de pesquisa inválidos ou nulos!");
        alert.setContentText(this.messageBody);
        alert.showAndWait();
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

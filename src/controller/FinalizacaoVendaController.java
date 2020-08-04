package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import model.entities.Viagem;
import view.util.TipoEspecial;

public class FinalizacaoVendaController {
    @FXML Label lbLinha, lbCidadeOrigem, lbCidadeDestino, lbHorarioSaida, lbValorViagem, lbValorSeguro;

    private Viagem chosenViagem;
    private TipoEspecial clientType;

    @FXML
    private void initialize(){
    }

    private void setInfoToView(){
        this.lbLinha.setText(chosenViagem.getLinhaName());
        this.lbCidadeOrigem.setText(chosenViagem.getCidadeOrigem());
        this.lbCidadeDestino.setText(chosenViagem.getCidadeDestino());
        this.lbHorarioSaida.setText(chosenViagem.getHorarioSaida());
        //this.lbValorViagem.setText(String.valueOf(chosenViagem.getValueViagem()));
        //this.lbValorSeguro.setText(String.valueOf(chosenViagem.getValueSeguroViagem()));
    }

    public Viagem getChosenViagem() {
        return chosenViagem;
    }

    public void setChosenViagem(Viagem chosenViagem) {
        this.chosenViagem = chosenViagem;
        this.setInfoToView();
    }

    public TipoEspecial getChonsenSitId() {
        return clientType;
    }

    public void setChonsenSitId(TipoEspecial chonsenSitId) {
        this.clientType = chonsenSitId;
    }
}

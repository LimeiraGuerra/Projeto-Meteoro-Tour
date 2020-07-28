package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.entities.Passagem;
import model.entities.Viagem;
import view.loader.LinhaLoader;
import view.loader.TrechoLoader;

public class VendasController {

    @FXML Button btnAssento01, btnAssento02, btnAssento03, btnAssento04, btnAssento05, btnAssento06,
            btnAssento07, btnAssento08, btnAssento09, btnAssento10, btnAssento11, btnAssento12, btnAssento13,
            btnAssento14, btnAssento15, btnAssento16, btnAssento17, btnAssento18, btnAssento19, btnAssento20,
            btnAssento21, btnAssento22, btnAssento23, btnAssento24, btnAssento25, btnAssento26, btnAssento27,
            btnAssento28, btnAssento29, btnAssento30, btnAssento31, btnAssento32, btnAssento33, btnAssento34,
            btnAssento35, btnAssento36, btnAssento37, btnAssento38, btnAssento39, btnAssento40, btnAssento41,
            btnAssento42, btnAssento43, btnAssento44;
    @FXML Menu menuGerenciar;
    @FXML AnchorPane popupReagendamento;
    @FXML TextField txtFieldOrigem, txtFieldDestino;
    @FXML MenuItem menuOptPassagens;

    private Passagem passagemReagendamento;

    @FXML
    private void initialize(){
        /* todo tabela*/
    }

    public void setAdminPrivileges() {
        menuGerenciar.setDisable(false);
    }

    public void setModeReagendamento() {
        this.setInfoInFields();
        this.toggleModeReagendamento(true);
    }

    public void cancelModeReagendamento(ActionEvent actionEvent) {
        this.toggleModeReagendamento(false);
    }

    private void setInfoInFields(){
        txtFieldOrigem.setText(this.passagemReagendamento.getViagem().getCidadeOrigem());
        txtFieldDestino.setText(this.passagemReagendamento.getViagem().getCidadeDestino());
    }

    private void toggleModeReagendamento(boolean mode) {
        txtFieldOrigem.setDisable(mode);
        txtFieldDestino.setDisable(mode);
        popupReagendamento.setVisible(mode);
    }

    public void openBuscarPassagens(ActionEvent actionEvent) {
        System.out.println("aaaaaaa");
        //todo
        //Abre a modal Buscar Passagem
        //Apenas teste
        Viagem testeViagem = new Viagem();
        testeViagem.setCidadeOrigem("Descalvado");
        testeViagem.setCidadeDestino("São Carlos");
        Passagem testePassagem = new Passagem();
        testePassagem.setViagem(testeViagem);
        this.passagemReagendamento = testePassagem;
        this.setModeReagendamento();
    }

    public void openTrecho(ActionEvent actionEvent) {
        TrechoLoader janelaTrecho = new TrechoLoader();
        janelaTrecho.start();
        Stage stage = (Stage) txtFieldDestino.getScene().getWindow();
        stage.close();
    }

    public void openLinha(ActionEvent actionEvent) {
        LinhaLoader janelaLinha = new LinhaLoader();
        janelaLinha.start();
        Stage stage = (Stage) txtFieldDestino.getScene().getWindow();
        stage.close();
    }
}

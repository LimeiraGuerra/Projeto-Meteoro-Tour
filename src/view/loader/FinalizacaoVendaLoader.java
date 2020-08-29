package view.loader;

import controller.FinalizacaoVendaController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.entities.Passagem;
import model.entities.Viagem;
import view.util.TipoEspecial;

import java.io.IOException;

public class FinalizacaoVendaLoader {
    private boolean success = false;
    private Passagem passagemR;

    public FinalizacaoVendaLoader(Passagem passagemReagendamento) {
        this.passagemR = passagemReagendamento;
    }

    public FinalizacaoVendaLoader() {
    }

    public void start(Viagem viagem, TipoEspecial clientType, String sitId){
        try{
            FXMLLoader loader = new FXMLLoader();
            Pane graph = loader.load(getClass().getResource("/view/fxml/FinalizacaoVenda.fxml").openStream());
            FinalizacaoVendaController ctrl = loader.getController();

            ctrl.setChosenViagem(viagem);
            ctrl.setClientType(clientType);
            ctrl.setChosenSitId(sitId);
            if (this.passagemR != null){ctrl.setPassagemR(this.passagemR);}

            Stage stage = new Stage();
            stage.setScene(new Scene(graph));
            stage.setTitle("Finalizar Compra");
            stage.setResizable(false);
            stage.sizeToScene();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();

            this.success = ctrl.isSoldSuccess();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public boolean isSoldSuccess(){
        return success;
    }
}

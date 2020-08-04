package view.loader;

import controller.FinalizacaoVendaController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.entities.Viagem;
import view.util.TipoEspecial;

import java.io.IOException;

public class FinalizacaoVendaLoader {

    public void start(Viagem viagem, TipoEspecial clientType){
        try{
            FXMLLoader loader = new FXMLLoader();
            Pane graph = loader.load(getClass().getResource("../fxml/FinalizacaoVenda.fxml").openStream());
            FinalizacaoVendaController ctrl = loader.getController();

            ctrl.setChosenViagem(viagem);
            ctrl.setChonsenSitId(clientType);

            Stage stage = new Stage();
            stage.setScene(new Scene(graph, 252, 363));
            stage.setTitle("Finalizar Compra");
            stage.setResizable(false);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

package view.loader;

import controller.EmissaoBilheteController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.entities.Passagem;

import java.io.IOException;

public class EmissaoBilheteLoader {
    public void start(Passagem passagem){
        try{
            FXMLLoader loader = new FXMLLoader();
            Pane graph = loader.load(getClass().getResource("/view/fxml/EmissaoBilhete.fxml").openStream());
            EmissaoBilheteController ctrl = loader.getController();

            ctrl.setPassagem(passagem);
            Stage stage = new Stage();
            stage.setScene(new Scene(graph));
            stage.getIcons().add(new Image(getClass().getResourceAsStream( "/resources/onibus.png" )));
            stage.setTitle("Meteoro Tour - Impressão de Bilhete");
            stage.setResizable(false);
            stage.sizeToScene();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

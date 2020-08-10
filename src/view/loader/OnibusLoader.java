package view.loader;

import controller.TrechoController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

import static javafx.fxml.FXMLLoader.load;

public class OnibusLoader {
    public void start(){
        FXMLLoader loader = new FXMLLoader();
        try{
            Pane graph = load(getClass().getResource("/view/fxml/Onibus.fxml"));
            Scene scene = new Scene(graph);
            Stage stage = new Stage();
            stage.setTitle("Meteoro Tour - Ônibus");
            OnibusLoader onibusController = loader.getController();

            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setResizable(false);
            stage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

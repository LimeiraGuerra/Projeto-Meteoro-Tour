package view.loader;

import controller.TrechoController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

import static javafx.fxml.FXMLLoader.*;

public class TrechoLoader {
    public void start(){
        FXMLLoader loader = new FXMLLoader();
        try{
            Pane grafico = load(getClass().getResource("/view/fxml/Trecho.fxml"));
            Scene cena = new Scene(grafico);
            Stage stage = new Stage();
            stage.setResizable(false);
            stage.sizeToScene();
            stage.setTitle("Meteoro Tour - Trechos");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(cena);
            stage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

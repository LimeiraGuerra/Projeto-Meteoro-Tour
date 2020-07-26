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

public class WindowTrecho {
    public void start(){
        FXMLLoader loader = new FXMLLoader();
        try{
            Pane grafico = load(getClass().getResource("../fxml/Trecho.fxml"));
            Scene cena = new Scene(grafico);
            Stage stage = new Stage();
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setTitle("Trechos");
            TrechoController trechoController = loader.getController();

            stage.setScene(cena);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

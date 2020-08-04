package view.loader;

import controller.LinhaController;
import controller.TrechoController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

import static javafx.fxml.FXMLLoader.load;

public class LinhaLoader {
    public void start(){
        FXMLLoader loader = new FXMLLoader();
        try{
            Pane grafico = load(getClass().getResource("../fxml/Linha.fxml"));
            Scene cena = new Scene(grafico);
            Stage stage = new Stage();
            stage.setResizable(false);
            stage.setTitle("Linhas");
            LinhaController trechoController = loader.getController();
            stage.setScene(cena);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

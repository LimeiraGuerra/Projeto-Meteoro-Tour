package view.loader;

import controller.FuncionarioController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

import static javafx.fxml.FXMLLoader.load;

public class FuncionarioLoader {
    public void start(){
        try{
            Pane graph = load(getClass().getResource("/view/fxml/Funcionario.fxml"));
            Scene scene = new Scene(graph);
            Stage stage = new Stage();
            stage.setTitle("Meteoro Tour - Funcionários");
            stage.getIcons().add(new Image(getClass().getResourceAsStream( "/resources/onibus.png" )));
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setResizable(false);
            stage.sizeToScene();
            stage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

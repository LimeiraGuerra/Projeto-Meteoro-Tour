package view.loader;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class RelatorioLoader {
    public void start(){
        try{
            FXMLLoader loader = new FXMLLoader();
            Pane graph = loader.load(getClass().getResource("/view/fxml/Relatorio.fxml").openStream());

            Stage stage = new Stage();
            stage.setScene(new Scene(graph));
            stage.getIcons().add(new Image(getClass().getResourceAsStream( "/resources/onibus.png" )));
            stage.setTitle("Meteoro Tour - Relatórios");
            stage.setResizable(false);
            stage.sizeToScene();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

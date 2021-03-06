package view.loader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.IOException;
import static javafx.fxml.FXMLLoader.load;

public class LinhaLoader {
    public void start(){
        try{
            Pane grafico = load(getClass().getResource("/view/fxml/Linha.fxml"));
            Scene cena = new Scene(grafico);
            Stage stage = new Stage();
            stage.getIcons().add(new Image(getClass().getResourceAsStream( "/resources/onibus.png" )));
            stage.setResizable(false);
            stage.sizeToScene();
            stage.setTitle("Meteoro Tour - Linhas");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(cena);
            stage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

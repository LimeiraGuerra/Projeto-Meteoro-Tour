package view.loader;

import controller.PassagemController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.entities.Passagem;

import java.io.IOException;

public class PassagemLoader {
    private Passagem passagemReagendamento;
    public void start(){
        try{
            FXMLLoader loader = new FXMLLoader();
            Pane graph = loader.load(getClass().getResource("/view/fxml/Passagem.fxml").openStream());
            PassagemController ctrl = loader.getController();

            Stage stage = new Stage();
            stage.setScene(new Scene(graph));
            stage.getIcons().add(new Image(getClass().getResourceAsStream( "/resources/onibus.png" )));
            stage.setTitle("Meteoro Tour - Busca de Passagens");
            stage.setResizable(false);
            stage.sizeToScene();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
            this.passagemReagendamento = ctrl.getPassagemReagendamento();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Passagem getPassagemReagendamento() {
        return passagemReagendamento;
    }

    public void setPassagemReagendamento(Passagem passagemReagendamento) {
        this.passagemReagendamento = passagemReagendamento;
    }
}

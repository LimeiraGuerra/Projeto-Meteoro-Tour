package view.loader;

import controller.ModalAvisoController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.DialogPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class ModalAvisoLoader {
    private String message = "";

    public ModalAvisoLoader(String msg){
        this.message = msg;
        this.start();
    }

    public ModalAvisoLoader(){}

    public void start(){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxml/ModalAviso.fxml"));
            Pane graph = loader.load();
            ModalAvisoController ctrl = loader.getController();
            ctrl.setErrorMessage(this.message);

            Stage stage = new Stage();
            stage.setScene(new Scene(graph, 352, 111));
            stage.setTitle("Erro!");
            stage.setResizable(false);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

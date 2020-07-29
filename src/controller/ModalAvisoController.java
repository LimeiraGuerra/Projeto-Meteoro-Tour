package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class ModalAvisoController {
    @FXML Label lbMessage;

    public void closeModal(ActionEvent actionEvent) {
        Stage stage = (Stage) lbMessage.getScene().getWindow();
        stage.close();
    }

    public void setErrorMessage (String msg){
        lbMessage.setText(msg);
    }
}

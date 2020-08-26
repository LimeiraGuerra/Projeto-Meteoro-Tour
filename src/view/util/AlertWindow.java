package view.util;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;

public class AlertWindow {

    public static void informationAlerta(String msg, String header){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Aviso!");
        alert.setHeaderText(header);
        alert.setContentText(msg);
        alert.showAndWait();
    }

    public static boolean verificationAlert(String msg, String header){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmação!");
        alert.setHeaderText(header);
        alert.setContentText(msg);
        Optional<ButtonType> result = alert.showAndWait();
        return result.get() == ButtonType.OK;
    }

    public static void errorAlert(String msg, String header){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erro!");
        alert.setHeaderText(header);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}

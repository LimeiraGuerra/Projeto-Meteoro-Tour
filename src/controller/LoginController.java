package controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import model.entities.Vendedor;
import javafx.stage.Stage;
import model.usecases.LoginUC;
import view.loader.VendasLoader;


import java.net.URL;
import java.util.ResourceBundle;


public class LoginController implements Initializable {
    @FXML private PasswordField txtSenha;
    @FXML private Label lbIncorreto;
    @FXML private ToggleButton tbAdm;
    @FXML private Pane paneLogin;

    private LoginUC loginUc = new LoginUC();
    private Vendedor user;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        lbIncorreto.setVisible(false);
        paneLogin.setVisible(false);
    }

    private void returnAdmin(){
        user = loginUc.getAdministrador();
    }

    private void closeStage(){
        Stage stage = (Stage) lbIncorreto.getScene().getWindow();
        stage.close();
    }
    @FXML
    private void login(ActionEvent actionEvent) {
        if (loginUc.isCheckPassword(txtSenha.getText())){
            VendasLoader janelaVendas = new VendasLoader(user);
            janelaVendas.start();
            closeStage();
        }
        else{
            lbIncorreto.setVisible(true);
            txtSenha.clear();
        }
    }

    @FXML
    private void onAdmin(ActionEvent actionEvent) {
        returnAdmin();
        boolean btnAdm = tbAdm.isSelected();
        if (btnAdm){
            paneLogin.setVisible(true);
        }
    }

    @FXML
    private void onVend(ActionEvent actionEvent) {
        user = new Vendedor();
        VendasLoader janelaVendas = new VendasLoader(user);
        janelaVendas.start();
        closeStage();
    }

    @FXML
    private void close(MouseEvent mouseEvent) {
        Platform.exit();
        System.exit(0);
    }


}

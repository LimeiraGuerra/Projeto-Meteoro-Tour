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
    @FXML private RadioButton rbAdm;
    @FXML private RadioButton rbVend;
    @FXML private Pane paneLogin;

    private ToggleGroup toggleGroup = new ToggleGroup();
    private LoginUC loginUc = new LoginUC();
    private Vendedor user;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setToggleGroup();
        lbIncorreto.setVisible(false);
        paneLogin.setVisible(false);
    }

    private void setToggleGroup(){
        rbAdm.setToggleGroup(toggleGroup);
        rbVend.setToggleGroup(toggleGroup);
    }
    private void returnAdmin(){
        user = loginUc.getAdministrador();
    }

    private void closeStage(){
        Stage stage = (Stage) lbIncorreto.getScene().getWindow();
        stage.close();
    }
    public void login(ActionEvent actionEvent) {
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

    public void onAdmin(ActionEvent actionEvent) {
        returnAdmin();
        boolean radioAdm = rbAdm.isSelected();
        if (radioAdm){
            paneLogin.setVisible(true);
        }
    }

    public void onVend(ActionEvent actionEvent) {
        user = new Vendedor();
        boolean radioVend = rbVend.isSelected();
        if(radioVend){
            paneLogin.setVisible(false);
            VendasLoader janelaVendas = new VendasLoader(user);
            janelaVendas.start();
            closeStage();
        }
    }

    public void close(MouseEvent mouseEvent) {
        Platform.exit();
        System.exit(0);
    }


}

package controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import model.Administrador;
import model.Vendedor;
import view.loader.WindowTrecho;

import java.net.URL;
import java.util.ResourceBundle;


public class LoginController implements Initializable {
    @FXML private PasswordField txtSenha;
    @FXML private Label lbIncorreto;
    @FXML private RadioButton rbAdm;
    @FXML private RadioButton rbVend;
    @FXML private Pane paneLogin;
    private ToggleGroup toggleGroup = new ToggleGroup();
    private Administrador adm;
    private Vendedor vend;


    @FXML public Label lbTeste;

    public void setAdmin(){
        adm = new Administrador("ADMINISTRADOR", "1234");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        rbAdm.setToggleGroup(toggleGroup);
        rbVend.setToggleGroup(toggleGroup);
        lbTeste.setVisible(false);
        lbIncorreto.setVisible(false);
        paneLogin.setVisible(false);
    }

    public void login(ActionEvent actionEvent) {
        if (txtSenha.getText().equals(adm.getSenha())){
            //chama outra tela aqui de vendaaa, a inicial
            //Chama tela de vendedor aqui!
            lbTeste.setVisible(true);
        }
        else{
            lbTeste.setVisible(false);
            lbIncorreto.setVisible(true);
        }
    }

    public void onAdmin(ActionEvent actionEvent) {
        setAdmin();
        boolean radioAdm = rbAdm.isSelected();
        if (radioAdm){
            paneLogin.setVisible(true);
        }
    }

    public void onVend(ActionEvent actionEvent) {
        vend = new Vendedor();
        boolean radioVend = rbVend.isSelected();
        if(radioVend){
            paneLogin.setVisible(false);
            lbTeste.setVisible(true);
            //Chama tela de vendedor aqui!

        }
    }

    public void close(MouseEvent mouseEvent) {
        Platform.exit();
        System.exit(0);
    }

    public void trecho(ActionEvent actionEvent) {
        WindowTrecho janelaTrecho = new WindowTrecho();
        janelaTrecho.start();
    }
}

package controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import model.Administrador;
import java.net.URL;
import java.util.ResourceBundle;


public class LoginController implements Initializable {
    @FXML public PasswordField txtSenha;
    @FXML public TextField txtUser;
    @FXML public Button btEntrar;
    @FXML public Label lbIncorreto;
    @FXML public ImageView imgEntrar;
    @FXML public ImageView imgCadeado;
    @FXML public RadioButton rbAdm;
    @FXML public RadioButton rbVend;
    private ToggleGroup toggleGroup = new ToggleGroup();

    @FXML public Label lbTeste;
    private Administrador adm = new Administrador();

    public void setAdminTeste(){
        adm.setNome("Adm");
        adm.setSenha("1234");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setAdminTeste();
        rbAdm.setToggleGroup(toggleGroup);
        rbVend.setToggleGroup(toggleGroup);
        txtSenha.setVisible(false);
        lbIncorreto.setVisible(false);
        txtUser.setVisible(false);
        txtUser.setVisible(false);
        btEntrar.setVisible(false);
        imgCadeado.setVisible(false);
        imgEntrar.setVisible(false);
        lbTeste.setVisible(false);

    }

    public void login(ActionEvent actionEvent) {
        if (txtSenha.getText().equals(adm.getSenha())){
            //chama outra tela aqui de vendaaa, a inicial
            lbTeste.setVisible(true);
        }
        else{
            lbTeste.setVisible(false);
            lbIncorreto.setVisible(true);
        }
    }

    public void onAdmin(ActionEvent actionEvent) {
        //toggleGroup.getSelectedToggle();
        boolean radioAdm = rbAdm.isSelected();
        if ( radioAdm == true){
            txtUser.setVisible(true);
            txtUser.setText("ADMINISTRADOR");
            txtSenha.setVisible(true);
            btEntrar.setVisible(true);
            imgEntrar.setVisible(true);
            imgCadeado.setVisible(true);
            lbTeste.setVisible(false);
        }
    }

    public void onVend(ActionEvent actionEvent) {
        //toggleGroup.getSelectedToggle();
        boolean radioVend = rbVend.isSelected();
        if(radioVend == true){
            lbIncorreto.setVisible(false);
            txtUser.setVisible(false);
            txtSenha.setVisible(false);
            btEntrar.setVisible(false);
            imgCadeado.setVisible(false);
            imgEntrar.setVisible(false);
            lbTeste.setVisible(true);
            //Chama tela de vendedor aqui?Não sei, ai
            // O Q TÔ FAZENDO?
        }
    }

    public void close(MouseEvent mouseEvent) {
        Platform.exit();
        System.exit(0);
    }
}

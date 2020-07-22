package controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import model.Administrador;

import java.net.URL;
import java.util.ResourceBundle;


public class LoginController implements Initializable {
    @FXML public PasswordField txtSenha;
    @FXML public TextField txtUser;
    @FXML public Button btEntrar;
    @FXML public Label lbIncorreto;
    @FXML public ComboBox cbTipo;
    @FXML public ImageView imgEntrar;
    @FXML public ImageView imgCadeado;
    @FXML public Label lbTeste;
    private Administrador adm = new Administrador();

    public void setAdminTeste(){
        adm.setNome("Adm");
        adm.setSenha("1234");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setAdminTeste();
        fillComboBox();
        checkTipes();
        txtSenha.setVisible(false);
        lbIncorreto.setVisible(false);
        txtUser.setVisible(false);
        txtUser.setVisible(false);
        btEntrar.setVisible(false);
        imgCadeado.setVisible(false);
        imgEntrar.setVisible(false);
        lbTeste.setVisible(false);

    }

    private void checkTipes() {
        cbTipo.valueProperty().addListener(new ChangeListener<String>() {

            public void changed(ObservableValue<? extends String> observableValue, String s, String s1) {
                 if (s1.equals("Administrador")){
                     txtUser.setVisible(true);
                     txtUser.setText("ADMINISTRADOR");
                     txtSenha.setVisible(true);
                     btEntrar.setVisible(true);
                     imgCadeado.setVisible(true);
                     imgEntrar.setVisible(true);
                     lbTeste.setVisible(false);
            }
                 if (s1.equals("Vendedor")){
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
        });
    }

    private void fillComboBox() {
        ObservableList<String> tipos = FXCollections.observableArrayList();
        tipos.add("Vendedor");
        tipos.add("Administrador");
        cbTipo.setItems(tipos);
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


}

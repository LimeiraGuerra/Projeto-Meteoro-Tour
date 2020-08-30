package view.loader;

import controller.VendasController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.entities.Administrador;
import model.entities.Vendedor;

import java.io.IOException;

public class VendasLoader {
    private Vendedor usuario;

    public VendasLoader(){}

    public VendasLoader(Vendedor usuario){
        this.usuario = usuario;
    }

    public void start(){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/fxml/Vendas.fxml"));
            VBox graph = loader.load();
            VendasController ctrl = loader.getController();

            if(isVendedorAdmin()) {ctrl.setAdminPrivileges();}

            Stage stage = new Stage();
            stage.setScene(new Scene(graph));
            stage.getIcons().add(new Image(getClass().getResourceAsStream( "/resources/onibus.png" )));
            ctrl.setScene(stage.getScene()); //Passo a cena para o controle para usar css selector
            stage.setTitle("Meteoro Tour - Venda de Passagens");
            stage.setResizable(false);
            stage.sizeToScene();
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean isVendedorAdmin() {
        return this.usuario instanceof Administrador;
    }
}

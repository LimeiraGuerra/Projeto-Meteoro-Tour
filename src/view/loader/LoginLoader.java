package view.loader;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class LoginLoader extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        Pane sceneGraph = loader.load(getClass().getResource("/view/fxml/Login.fxml"));
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream( "/resources/onibus.png" )));
        Scene scene = new Scene(sceneGraph);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
}

package view.loader;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;

import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        Pane sceneGraph = loader.load(getClass().getResource("../fxml/Login.fxml"));
        primaryStage.initStyle(StageStyle.UNDECORATED);
        Scene scene = new Scene(sceneGraph);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
}

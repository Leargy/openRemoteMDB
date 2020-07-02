package nook;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Nook extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent content = FXMLLoader.load(getClass().getResource("raw_scenes\\loginScene.fxml"));
        Scene currentScene = new Scene(content, 324, 403);
        primaryStage.setTitle("RemoteMDB | Login");
        primaryStage.setResizable(false);
        primaryStage.setScene(currentScene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}

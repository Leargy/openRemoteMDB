package sample.dialog_windows;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;

public class ConnectionSceneFactory implements WindowsFactory {

    @Override
    public Scene createScene() {
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/sample/assets/windows/connectionScene.fxml"));
        }catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return new Scene(root, 320, 400);
    }
}

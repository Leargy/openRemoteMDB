package sample.dialog_windows;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;

public class InteructionSceneFactory implements WindowsFactory {
    @Override
    public Scene createScene() {
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/sample/assets/windows/interactionScene.fxml"));
        }catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return new Scene(root, 390, 470);
    }
}

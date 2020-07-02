package sample.dialog_windows;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;

public class AuthorizationSceneFactory implements WindowsFactory {

    @Override
    public Scene createScene() {
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/sample/assets/windows/logIn.fxml"));
        }catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return new Scene(root, 332, 412);
    }
}

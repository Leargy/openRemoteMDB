package sample.dialogWindows;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;

public class ConnectionWindowsFactory implements WindowsFactory {

    @Override
    public Scene createScene() {
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/sample/asserts/windows/ipAndPort.fxml"));
        }catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return new Scene(root, 320, 400);
    }
}

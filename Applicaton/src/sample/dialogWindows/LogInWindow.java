package sample.dialogWindows;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuBar;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.buttons.IButton;

public class LogInWindow extends Dialog {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField login_field;

    @FXML
    private MenuBar laguage_menu;

    @FXML
    private Button sign_in_button;

    @FXML
    private Button sign_up_button;

    @FXML
    private PasswordField password_field;

    @FXML
    void initialize() {
    }
    @Override
    public void renderWindow() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/sample/assets/windows/logIn.fxml"));
        thisStage.setTitle("RemoteMDB");
        thisStage.setScene(new Scene(root, 320, 400));
        thisStage.setResizable(false);
        thisStage.show();
    }

    @Override
    public IButton getButton() {
        return null;
    }
}

package sample.dialog_windows;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuBar;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LogInWindow extends Dialog {
    private WindowsFactory authorizationWindowFactory;
//    private Scene logInScene;

    public LogInWindow() {
        authorizationWindowFactory = new AuthorizationSceneFactory();
    }

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
    private Button out_button;

    @FXML
    void initialize() {
        out_button.setOnAction(event -> {
        });
        sign_in_button.setOnAction(event -> {
            System.out.println("gay");
        });
    }
    @Override
    public void renderWindow() {
        thisStage.setScene(getScene());
        thisStage.show();
    }

    @Override
    public Scene getScene() {
        return authorizationWindowFactory.createScene();
    }

    @Override
    public String getData() {
        return "";
    }

    @Override
    public void initAlertBox(String alertMessage) {
        System.out.println("ALERT"); //TODO: make logic ow alert windows
    }
}

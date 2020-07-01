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
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.buttons.ConnectionIButton;
import sample.buttons.IButton;

public class IpAndPortInputWindow extends Dialog {
    private IButton connectionActioner = getButton();
    private Scene logInScene;
    private Scene ipPortInputScene;
    private WindowsFactory connectionWindowFactory;
    private WindowsFactory authorizationWindowFactory;

    public IpAndPortInputWindow() {
        connectionWindowFactory = new ConnectionWindowsFactory();
        authorizationWindowFactory = new AuthorizationSceneFactory();
    }

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField ip_field;

    @FXML
    private TextField port_field;

    @FXML
    private Button connectionButton;

    @FXML
    private MenuBar language_menu_button;

    @FXML
    void initialize() {
        connectionButton.setOnAction(event -> {
            thisStage.setScene(authorizationWindowFactory.createScene());
            connectionActioner.click();
            ((Stage) connectionButton.getScene().getWindow()).close();
        });
        ip_field.setOnAction( event -> {
            System.out.println(" heeeee");
        });
    }

    @Override
    public void renderWindow() throws IOException{
        thisStage = new Stage();
        thisStage.setTitle("RemoteMDB");
        thisStage.setScene(connectionWindowFactory.createScene());
        thisStage.setResizable(false);
        thisStage.show();
    }

    @Override
    public IButton getButton() {
        return new ConnectionIButton();
    }
}

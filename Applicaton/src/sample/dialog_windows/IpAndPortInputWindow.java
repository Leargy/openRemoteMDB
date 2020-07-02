package sample.dialog_windows;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TextField;
import sample.buttons.ConnectionIButton;
import sample.buttons.IButton;

public class IpAndPortInputWindow extends Dialog{
    private static IButton buttonActioner;
    private ConnectionSceneFactory connectionSceneFactory;

    public IpAndPortInputWindow() {
        connectionSceneFactory = new ConnectionSceneFactory();
    }
    public IpAndPortInputWindow setButtonActioner(IButton button) {
        buttonActioner = button;
        return this;
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
            buttonActioner.click();
            ((ConnectionIButton)buttonActioner).setIp(ip_field.getText());
            ((ConnectionIButton)buttonActioner).setPort(port_field.getText());
//            ((Stage) connectionButton.getScene().getWindow()).close();
        });
    }

    @Override
    public void renderWindow() {
        thisStage.setScene(getScene());
        thisStage.show();
    }

    @Override
    public Scene getScene() {
        return connectionSceneFactory.createScene();
    }

    @Override
    public String getData() {
        return null;
    }

    @Override
    public void initAlertBox(String alertMessage) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Connection");
        alert.setHeaderText(null);
        alert.setContentText(alertMessage);
        alert.showAndWait();
    }
}

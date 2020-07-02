package sample.dialog_windows.Controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TextField;
import sample.buttons.IButton;
import sample.dialog_windows.Commander;
import sample.dialog_windows.ConnectionSceneFactory;
import sample.dialog_windows.Dialog;
import sample.dialog_windows.communication.ApplicationParcel;

public class IpAndPortInputWindowController extends Dialog {
    private static IButton buttonActioner;
    private ConnectionSceneFactory connectionSceneFactory;
    private static Commander totalCommander;

    public IpAndPortInputWindowController() {
        connectionSceneFactory = new ConnectionSceneFactory();
    }

//    public IpAndPortInputWindow setButtonActioner(IButton button) {
//        buttonActioner = button;
//        return this;
//    }

    public IpAndPortInputWindowController setCommander(Commander totalCommander) {
        this.totalCommander = totalCommander;
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
//            ((ConnectionIButton)buttonActioner).setIp(ip_field.getText());
//            ((ConnectionIButton)buttonActioner).setPort(port_field.getText());
//            buttonActioner.click();
            totalCommander.setConnection(new ApplicationParcel("ip=" + ip_field.getText() + " " + "port=" + port_field.getText()));
//            ((Stage) connectionButton.getScene().getWindow()).close();
        });
    }

    @Override
    public void renderWindow() {
        thisStage.hide();
//        thisStage.setResizable(true);
        thisStage.setScene(getScene());
//        thisStage.setResizable(false);
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

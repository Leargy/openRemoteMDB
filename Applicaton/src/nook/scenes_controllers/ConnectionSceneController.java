package nook.scenes_controllers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import locale.Localizator;

import java.util.Locale;
import java.util.ResourceBundle;

public class ConnectionSceneController {
    protected static final String CORRECT_IP_PATTERN = "(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)";
    protected static final int DEFAULT_PORT = 0xdead;
    protected static final int MIN_FREE_PORT = 50000;
    @FXML private Text connect_ip_text;
    @FXML private Text connect_port_text;
    @FXML private Button connect_btn;
    @FXML private Menu connect_lang_choser;
    // text fields validation sign
    @FXML private Label connect_port_sign;
    @FXML private Label connect_ip_sign;
    @FXML private MenuItem connect_change_es;
    @FXML private MenuItem connect_change_sl;
    @FXML private MenuItem connect_change_uk;
    @FXML private MenuItem connect_change_ru;
    private Locale currentLocale = Localizator.DEFAULT;
    @FXML
    public void initialize() {
        // initialize menu items with images
        ImageView rusView = new ImageView(new Image("nook\\assets\\images\\misc\\ussr.png"));
        connect_change_ru.setGraphic(rusView);
        ImageView espView = new ImageView(new Image("nook\\assets\\images\\misc\\panama.gif"));
        connect_change_es.setGraphic(espView);
        ImageView slvView = new ImageView(new Image("nook\\assets\\images\\misc\\slovain.gif"));
        connect_change_sl.setGraphic(slvView);
        ImageView ukrView = new ImageView(new Image("nook\\assets\\images\\misc\\ukrain.gif"));
        connect_change_uk.setGraphic(ukrView);
    }

    // Window components
    @FXML
    private MenuBar language_menu_button;
    @FXML
    private TextField connect_port;
    @FXML
    private TextField connect_ip;

    public void validateIP(ActionEvent actionEvent) {
        String onValidation = connect_ip.getText();
        if (onValidation == null || onValidation.isEmpty()) {
            connect_ip_sign.setText("Empty");
            connect_ip_sign.setTextFill(Color.GOLDENROD);
        } else if (checkIPv4Address(onValidation)) {
            connect_ip_sign.setText("Correct");
            connect_ip_sign.setTextFill(Color.GREENYELLOW);
        } else {
            connect_ip_sign.setText("Incorrect");
            connect_ip_sign.setTextFill(Color.DARKRED);
        }
    }

    protected static boolean checkIPv4Address(String address) {
        return (address != null && !address.isEmpty() && address.matches(CORRECT_IP_PATTERN));
    }

    protected static boolean checkFreePort(int port) {
        return (port < MIN_FREE_PORT)? false : true;
    }

    public void validatePort(ActionEvent actionEvent) {
        String onValidation = connect_port.getText();
        if (onValidation == null || onValidation.isEmpty()) {
            connect_port_sign.setText("Empty");
            connect_port_sign.setTextFill(Color.GOLDENROD);
        } else {
            Integer port = null;
            try {
                port = Integer.valueOf(onValidation);
            } catch (Throwable exception) {
                connect_port_sign.setText("Incorrect");
                connect_port_sign.setTextFill(Color.DARKRED);
                return;
            }
            if (checkFreePort(port)) {
                connect_port_sign.setText("Correct");
                connect_port_sign.setTextFill(Color.GREENYELLOW);
            } else {
                connect_port_sign.setText("Incorrect");
                connect_port_sign.setTextFill(Color.DARKRED);
            }
        }
    }

    @FXML
    public void changeLanguage(ActionEvent actionEvent) {
        Object menuItem = actionEvent.getSource();
        ResourceBundle newBundle = null;
        if (connect_change_ru.equals(menuItem))
            currentLocale = Localizator.RUSSIA_USSR;
        else if (connect_change_es.equals(menuItem))
            currentLocale = Localizator.SPAIN_PANAMA;
        else if (connect_change_sl.equals(menuItem))
            currentLocale = Localizator.SLOVENIAN_SLOVENIA;
        else if (connect_change_uk.equals(menuItem))
            currentLocale = Localizator.UKRAINIAN_UKRAINE;
        else currentLocale = Localizator.DEFAULT;
        newBundle = Localizator.changeLocale("locale.connection.ConnectionResources", currentLocale);
        updateText2Language(newBundle);
    }

    private void updateText2Language(ResourceBundle bundle) {
        if (bundle == null) return;
        // getting new translation
        String language = (String) bundle.getObject("Language");
        String ip = (String) bundle.getObject("IP");
        String port = (String) bundle.getObject("Port");
        String connect = (String) bundle.getObject("Connect");
        connect_lang_choser.setText(language);
        connect_ip_text.setText(ip);
        connect_port_text.setText(port);
        connect_btn.setText(connect);
    }

    public void connect(ActionEvent actionEvent) {
        Integer port = null;
        try {
            port = Integer.valueOf(connect_port.getText());
        } catch (NumberFormatException exception) {
            invokeAlert(currentLocale);
            return;
        }
        if (!checkIPv4Address(connect_ip.getText()) || !checkFreePort(port))
            invokeAlert(currentLocale);
        else {
            // TODO: invoke from client connection
        }
    }

    private final void invokeAlert(Locale currentLocale) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        ResourceBundle resources = Localizator.changeLocale("locale.connection.alerts.AlertsResources", currentLocale);
        String header = (String) resources.getObject("Invalid input data");
        String content = (String) resources.getObject("Invalid IP or Port");
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.show();
    }
}

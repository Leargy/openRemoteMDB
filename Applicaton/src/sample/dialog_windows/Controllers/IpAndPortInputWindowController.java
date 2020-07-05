package sample.dialog_windows.Controllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import locale.Localizator;
import sample.buttons.IButton;
import sample.dialog_windows.Commander;
import sample.dialog_windows.ConnectionSceneFactory;
import sample.dialog_windows.Dialog;
import sample.dialog_windows.communication.ApplicationParcel;


public class IpAndPortInputWindowController extends Dialog {
    private ConnectionSceneFactory connectionSceneFactory;
    private static Commander totalCommander;
    private static Stage progressStage = new Stage();
    private ConnectionProgress progressIndicator;

    private java.util.Locale currentLocale = Localizator.DEFAULT;

    public IpAndPortInputWindowController() {
        connectionSceneFactory = new ConnectionSceneFactory();
        progressIndicator = new ConnectionProgress();
    }

    public IpAndPortInputWindowController setCommander(Commander totalCommander) {
        this.totalCommander = totalCommander;
        return this;
    }

    @FXML private TextField ip_field;
    @FXML private TextField port_field;
    @FXML private Button connectionButton;
    @FXML private MenuBar language_menu_button;
    @FXML private Text connect_port_text;
    @FXML private Text connect_ip_text;
    @FXML private Menu connect_lang_choser;
    @FXML private MenuItem connect_change_ru;
    @FXML private MenuItem connect_change_es;
    @FXML private MenuItem connect_change_sl;
    @FXML private MenuItem connect_change_uk;

    @FXML
    void initialize() {
        // initialize menu items with images
        ImageView rusView = new ImageView(new Image(getClass().getResource("../../assets/images/ussr.png").toString()));
        connect_change_ru.setGraphic(rusView);
        ImageView espView = new ImageView(new Image(getClass().getResource("../../assets/images/panama.gif").toString()));
        connect_change_es.setGraphic(espView);
        ImageView slvView = new ImageView(new Image(getClass().getResource("../../assets/images/slovain.gif").toString()));
        connect_change_sl.setGraphic(slvView);
        ImageView ukrView = new ImageView(new Image(getClass().getResource("../../assets/images/ukrain.gif").toString()));
        connect_change_uk.setGraphic(ukrView);

        connectionButton.setOnAction(event -> {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    totalCommander.setConnection(new ApplicationParcel("ip=" + ip_field.getText() + " " + "port=" + port_field.getText()));
                }
            });
            try {
                progressIndicator.start(progressStage);
            }catch (Exception ex) {
//                          progressStage.close();
            }
        });
    }

    @Override
    public void renderWindow() {
//        thisStage.hide();
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
    public void setInfo(String info) {
    }

    @Override
    public void initAlertBox(String alertMessage) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Connection");
        alert.setHeaderText(null);
        alert.setContentText(alertMessage);
        alert.showAndWait();
//        Alert alert = new Alert(Alert.AlertType.ERROR);
//        ResourceBundle bundle = Localizator.changeLocale("locale.connection.alerts.AlertsResources", currentLocale);
//        alert.setTitle("Connection");
//        alert.setHeaderText((String) bundle.getObject("Invalid input data"));
//        alert.setContentText((String) bundle.getObject("Invalid IP or Port"));
//        alert.showAndWait();
    }

    @Override
    public void changingSignal() {
        progressStage.close();
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
        connectionButton.setText(connect);
    }
}

package sample.dialog_windows.Controllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import locale.Localizator;
import sample.buttons.IButton;
import sample.dialog_windows.AuthorizationSceneFactory;
import sample.dialog_windows.Commander;
import sample.dialog_windows.Dialog;
import sample.dialog_windows.WindowsFactory;

public class LogInWindowController extends Dialog {
    private static IButton buttonActioner;
    private WindowsFactory authorizationWindowFactory;
    public static final StringProperty nickFromTextField = new SimpleStringProperty();;
//    private Scene logInScene;
    private static Commander totalCommander;

    public LogInWindowController() {
        authorizationWindowFactory = new AuthorizationSceneFactory();
    }

    public void setPropertyListner(StringProperty mainWindowProperty) {
        nickFromTextField.bindBidirectional(mainWindowProperty);
    }

    public LogInWindowController setCommander(Commander totalCommander) {
        this.totalCommander = totalCommander;
        return this;
    }

    private java.util.Locale currentLocale = Localizator.DEFAULT;

    @FXML private TextField login_field;
    @FXML private MenuBar laguage_menu;
    @FXML private Button sign_in_button;
    @FXML private Button sign_up_button;
    @FXML private PasswordField password_field;
    @FXML private Button out_button;
    @FXML private Menu login_lang_choser;
    @FXML private MenuItem login_change_ru;
    @FXML private MenuItem login_change_es;
    @FXML private MenuItem login_change_sl;
    @FXML private MenuItem login_change_uk;
    @FXML private Text login_login_text;
    @FXML private Text login_pass_text;

    @FXML
    void initialize() {
        // initialize menu items with images
        ImageView rusView = new ImageView(new Image(getClass().getResource("../../assets/images/ussr.png").toString()));
        login_change_ru.setGraphic(rusView);
        ImageView espView = new ImageView(new Image(getClass().getResource("../../assets/images/panama.gif").toString()));
        login_change_es.setGraphic(espView);
        ImageView slvView = new ImageView(new Image(getClass().getResource("../../assets/images/slovain.gif").toString()));
        login_change_sl.setGraphic(slvView);
        ImageView ukrView = new ImageView(new Image(getClass().getResource("../../assets/images/ukrain.gif").toString()));
        login_change_uk.setGraphic(ukrView);


        out_button.setOnAction(event -> {
            totalCommander.back();
        });
        sign_in_button.setOnAction(event -> {

            nickFromTextField.set(login_field.getText());
            totalCommander.signIn(login_field.getText(), password_field.getText());
        });
        sign_up_button.setOnAction(event -> {

            nickFromTextField.set(login_field.getText());
            totalCommander.signUp(login_field.getText(), password_field.getText());
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
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Authorization");
        alert.setHeaderText(null);
        alert.setContentText(alertMessage);
        alert.showAndWait();
    }

    @FXML
    public void changeLanguage(ActionEvent actionEvent) {
        Object menuItem = actionEvent.getSource();
        ResourceBundle newBundle = null;
        if (login_change_ru.equals(menuItem))
            currentLocale = Localizator.RUSSIA_USSR;
        else if (login_change_es.equals(menuItem))
            currentLocale = Localizator.SPAIN_PANAMA;
        else if (login_change_sl.equals(menuItem))
            currentLocale = Localizator.SLOVENIAN_SLOVENIA;
        else if (login_change_uk.equals(menuItem))
            currentLocale = Localizator.UKRAINIAN_UKRAINE;
        else currentLocale = Localizator.DEFAULT;
        newBundle = Localizator.changeLocale("locale.login.LoginResources", currentLocale);
        updateText2Language(newBundle);
    }

    private void updateText2Language(ResourceBundle bundle) {
        if (bundle == null) return;
        // getting new translation
        String language = (String) bundle.getObject("Language");
        String login = (String) bundle.getObject("Login");
        String password = (String) bundle.getObject("Password");
        String sign_in = (String) bundle.getObject("Sign In");
        String sign_up = (String) bundle.getObject("Sign Up");
        String exit = (String) bundle.getObject("Exit");
        login_lang_choser.setText(language);
        login_login_text.setText(login);
        login_pass_text.setText(password);
        sign_in_button.setText(sign_in);
        sign_up_button.setText(sign_up);
        out_button.setText(exit);
    }
}

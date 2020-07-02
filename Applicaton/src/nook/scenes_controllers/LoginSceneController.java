package nook.scenes_controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import locale.Localizator;

import java.util.Locale;
import java.util.ResourceBundle;

public class LoginSceneController {
    @FXML private Menu login_lang_choser;
    @FXML private MenuItem login_change_ru;
    @FXML private MenuItem login_change_es;
    @FXML private MenuItem login_change_sl;
    @FXML private MenuItem login_change_uk;
    @FXML private TextField login_login_field;
    @FXML private Text login_login_text;
    @FXML private Text login_pass_text;
    @FXML private Button login_signin_btn;
    @FXML private Button login_signup_btn;
    @FXML private PasswordField login_pass_field;
    @FXML private MenuBar login_lang_menu;
    @FXML private Button login_exit_btn;

    private Locale currentLocale = Localizator.DEFAULT;

    @FXML
    public void initialize() {
        // initialize menu items with images
        ImageView rusView = new ImageView(new Image("sample/assets/images/ussr.png"));
        login_change_ru.setGraphic(rusView);
        ImageView espView = new ImageView(new Image("sample/assets/images/panama.gif"));
        login_change_es.setGraphic(espView);
        ImageView slvView = new ImageView(new Image("sample/assets/images/slovain.gif"));
        login_change_sl.setGraphic(slvView);
        ImageView ukrView = new ImageView(new Image("sample/assets/images/ukrain.gif"));
        login_change_uk.setGraphic(ukrView);
    }

    public void signInBtnClickHandle(ActionEvent actionEvent) {
    }

    public void signUpBtnClickHandle(ActionEvent actionEvent) {
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
        login_signin_btn.setText(sign_in);
        login_signup_btn.setText(sign_up);
        login_exit_btn.setText(exit);
    }
}

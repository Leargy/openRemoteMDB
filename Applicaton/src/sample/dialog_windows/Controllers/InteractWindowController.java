package sample.dialog_windows.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.text.Text;
import locale.Localizator;
import sample.dialog_windows.Dialog;

import java.util.ResourceBundle;

public class InteractWindowController extends Dialog {
    @FXML private Text interact_name;
    @FXML private Text interact_fullname;
    @FXML private Menu interact_lang_choser;
    @FXML private MenuItem interact_change_ru;
    @FXML private MenuItem interact_change_es;
    @FXML private MenuItem interact_change_sl;
    @FXML private MenuItem interact_change_uk;
    @FXML private Text interact_type;
    @FXML private Text interact_employees;
    @FXML private Text interact_annual;
    @FXML private Button interact_confirm_btn;
    @FXML private Button interact_delete_btn;
    @FXML private Text interact_zip_code;
    @FXML private Label interact_address_title;
    @FXML private Text interact_map_y;
    @FXML private Text interact_map_x;
    @FXML private Label interact_map_title;
    @FXML private Label interact_title;

    private java.util.Locale currentLocale = Localizator.DEFAULT;

    @Override
    public void renderWindow() {

    }

    @Override
    public Scene getScene() {
        return null;
    }

    @Override
    public String getData() {
        return null;
    }

    @Override
    public void initAlertBox(String alertMessage) {

    }

    @FXML
    public void changeLanguage(ActionEvent actionEvent) {
        Object menuItem = actionEvent.getSource();
        ResourceBundle newBundle = null;
        if (interact_change_ru.equals(menuItem))
            currentLocale = Localizator.RUSSIA_USSR;
        else if (interact_change_es.equals(menuItem))
            currentLocale = Localizator.SPAIN_PANAMA;
        else if (interact_change_sl.equals(menuItem))
            currentLocale = Localizator.SLOVENIAN_SLOVENIA;
        else if (interact_change_uk.equals(menuItem))
            currentLocale = Localizator.UKRAINIAN_UKRAINE;
        else currentLocale = Localizator.DEFAULT;
        newBundle = Localizator.changeLocale("locale.interact.InteractionResources", currentLocale);
        updateText2Language(newBundle);
    }

    private void updateText2Language(ResourceBundle bundle) {
        if (bundle == null) return;
        // getting new translation
        String title = (String) bundle.getObject("Organization Editor");
        String language = (String) bundle.getObject("Language");
        String name = (String) bundle.getObject("Name");
        String fullname = (String) bundle.getObject("Full Name");
        String type = (String) bundle.getObject("Type");
        String employees = (String) bundle.getObject("Employees Count");
        String annual = (String) bundle.getObject("Annual Turnover");
        String address = (String) bundle.getObject("Official Address");
        String zipcode = (String) bundle.getObject("Zip Code");
        String coordinates = (String) bundle.getObject("Coordinates");
        String position = (String) bundle.getObject("Position");
        String confirm = (String) bundle.getObject("Confirm");
        String delete = (String) bundle.getObject("Delete");
        interact_title.setText(title);
        interact_lang_choser.setText(language);
        interact_map_title.setText(address);
        interact_address_title.setText(address);
        interact_map_title.setText(coordinates);
        interact_name.setText(name);
        interact_fullname.setText(fullname);
        interact_zip_code.setText(zipcode);
        interact_annual.setText(annual);
        interact_type.setText(type);
        interact_confirm_btn.setText(confirm);
        interact_delete_btn.setText(delete);
        String interact_x = String.valueOf(interact_map_x.getText().charAt(interact_map_x.getText().length() - 1));
        String interact_y = String.valueOf(interact_map_y.getText().charAt(interact_map_y.getText().length() - 1));
        interact_map_x.setText(position + interact_x);
        interact_map_y.setText(position + interact_y);
    }
}

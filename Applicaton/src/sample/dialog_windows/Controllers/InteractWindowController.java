package sample.dialog_windows.Controllers;

import java.time.LocalDateTime;
import java.util.ResourceBundle;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import locale.Localizator;
import organization.Organization;
import organization.OrganizationWithUId;
import sample.dialog_windows.Commander;
import sample.dialog_windows.Dialog;
import sample.dialog_windows.InteructionSceneFactory;

public class InteractWindowController extends Dialog {
    private static Commander totalCommander;
    public final InteructionSceneFactory inteructionSceneFactory;
    public static final Stage stage = new Stage();
    public static final StringProperty organizationParams = new SimpleStringProperty();
    public static final ObservableList<String> langs = FXCollections.observableArrayList("Public", "Trust", "Open_join_stock_company", "Private_limited_company");
    private static Organization tempOrganization = null;

    public InteractWindowController() {
        inteructionSceneFactory = new InteructionSceneFactory();
        stage.setTitle("RemoteMDB");
        organizationParams.bindBidirectional(MainWindowController.organizationParams);
    }

    public void setRowOrganization(Organization organization) {
        tempOrganization = organization;
    }

    public InteractWindowController setCommander(Commander totalCommander) {
        this.totalCommander = totalCommander;
        return this;
    }

    @FXML private TextField name_field;
    @FXML private TextField full_name_field;
    @FXML private Text interact_name;
    @FXML private Text interact_fullname;
    @FXML private Menu interact_lang_choser;
    @FXML private MenuItem interact_change_ru;
    @FXML private MenuItem interact_change_es;
    @FXML private MenuItem interact_change_sl;
    @FXML private MenuItem interact_change_uk;
    @FXML private Text interact_type;
    @FXML private Text interact_employees;
    @FXML private TextField employees_count_field;
    @FXML private Text interact_annual;
    @FXML private TextField annual_turnover_field;
    @FXML private Button interact_confirm_btn;
    @FXML private Button interact_delete_btn;
    @FXML private TextField locate_z_field;
    @FXML private TextField locate_y_field;
    @FXML private TextField locate_x_field;
    @FXML private TextField zip_cod_field;
    @FXML private Text interact_zip_code;
    @FXML private Label interact_address_title;
    @FXML private TextField coord_y_field;
    @FXML private Text interact_map_y;
    @FXML private TextField coord_x_field;
    @FXML private Text interact_map_x;
    @FXML private Label interact_map_title;
    @FXML private ComboBox<String> type_combo_box;
    @FXML private Label interact_title;

    @FXML
    void initialize() {
        type_combo_box.setItems(langs);
        type_combo_box.setValue("");
        interact_confirm_btn.setOnAction(event -> {
            if(tempOrganization != null) {
                totalCommander.update(String.valueOf(tempOrganization.getID()), prepareParams());
            }else {
                totalCommander.insert(prepareParams());
            }
            stage.hide();

        });
        interact_delete_btn.setOnAction(event -> {
            totalCommander.remove(String.valueOf(tempOrganization.id));
            stage.hide();
        });
        decomposeInformation();
    }

    @Override
    public void renderWindow() {
        stage.setResizable(false);
        stage.setScene(getScene());
        stage.show();
    }

    @Override
    public Scene getScene() {
        return inteructionSceneFactory.createScene();
    }

    @Override
    public void setInfo(String info) {

    }

    @Override
    public void initAlertBox(String alertMessage) {
        stage.setOnCloseRequest(event -> {
            this.notifyAll();
        });
        interact_confirm_btn.setOnAction(event -> {

            organizationParams.setValue("name="+";");
            synchronized (this) {
                notify();
            }
        });

    }

    private java.util.Locale currentLocale = Localizator.DEFAULT;

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
        interact_employees.setText(employees);
        String interact_x = String.valueOf(interact_map_x.getText().charAt(interact_map_x.getText().length() - 1));
        String interact_y = String.valueOf(interact_map_y.getText().charAt(interact_map_y.getText().length() - 1));
        interact_map_x.setText(position + " " + interact_x);
        interact_map_y.setText(position + " " + interact_y);
    }

    public void decomposeInformation() {
        if (tempOrganization != null) {
            name_field.setText(tempOrganization.getName());
            full_name_field.setText(tempOrganization.getFullname());
            type_combo_box.setValue(tempOrganization.getType().toString());
            employees_count_field.setText(String.valueOf(tempOrganization.getEmployeesCount()));
            annual_turnover_field.setText(String.valueOf(tempOrganization.getAnnualTurnOver()));
            coord_x_field.setText(String.valueOf(tempOrganization.getCoordinates().getX()));
            coord_y_field.setText(String.valueOf(tempOrganization.getCoordinates().getY()));
            zip_cod_field.setText(tempOrganization.getAddress().getZipCode());
            locate_x_field.setText(String.valueOf(tempOrganization.getAddress().getTown().getX()));
            locate_y_field.setText(String.valueOf(tempOrganization.getAddress().getTown().getY()));
            locate_z_field.setText(String.valueOf(tempOrganization.getAddress().getTown().getZ()));
        }else {
            name_field.setText("");
            full_name_field.setText("");
            type_combo_box.setValue("");
            employees_count_field.setText("");
            annual_turnover_field.setText("");
            coord_x_field.setText("");
            coord_y_field.setText("");
            zip_cod_field.setText("");
            locate_x_field.setText("");
            locate_y_field.setText("");
            locate_z_field.setText("");
        }
    }

    @FXML
    private String prepareParams() {
        StringBuilder params = new StringBuilder();
        params.append("name=" + name_field.getText().replace(" ", "_") + ";");
        params.append("fullName=" + full_name_field.getText().replace(" ", "_") + ";");
        params.append("type=" + type_combo_box.getValue() + ";");
        params.append("employs=" + employees_count_field.getText() + ";");
        params.append("annual=" + annual_turnover_field.getText() + ";");
        params.append("date=" + LocalDateTime.now() + ";");
        params.append("cordx=" + coord_x_field.getText() + ";");
        params.append("cordy=" + coord_y_field.getText() + ";");
        params.append("zip=" + zip_cod_field.getText().replace(" ", "_") + ";");
        params.append("locx=" + locate_x_field.getText() + ";");
        params.append("locy=" + locate_y_field.getText() + ";");
        params.append("locz=" + locate_z_field.getText() + ";");
        if (tempOrganization != null) params.append("id=" + tempOrganization.getID() + ";");
        return params.toString();
    }
}

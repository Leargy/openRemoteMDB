package nook.scenes_controllers;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TextField;
import sample.dialog_windows.Dialog;

public class InteractSceneController extends Dialog {

    @FXML private ResourceBundle resources;
    @FXML private URL location;
    @FXML private TextField name_field;
    @FXML private TextField full_name_field;
    @FXML private MenuBar laguage_menu;
    @FXML private TextField employees_number_field;
    @FXML private TextField annual_turnover_field;
    @FXML private Button confirm_button;
    @FXML private Button delete_button;
    @FXML private TextField location_z;
    @FXML private TextField location_y;
    @FXML private TextField location_x;
    @FXML private TextField index_field;
    @FXML private TextField coordinates_y;
    @FXML private TextField coordinates_x;
    @FXML private ComboBox<?> type_combo_box;

    @FXML
    void initialize() {

    }

    @Override
    public void renderWindow() {

    }

    @Override
    public Scene getScene() {
        return null;
    }

    @Override
    public void setInfo(String info) {

    }

    @Override
    public void initAlertBox(String alertMessage) {

    }
}

package sample.dialog_windows.Controllers;

import java.net.URL;
import java.util.ResourceBundle;

import instructions.Decree;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import sample.buttons.IButton;
import sample.dialog_windows.Commander;
import sample.dialog_windows.Dialog;
import sample.dialog_windows.MainWindowFactory;
import sample.dialog_windows.WindowsFactory;

public class MainWindowController extends Dialog {
    private static IButton buttonActioner;
    private WindowsFactory mainWindowFactory;
    private static Commander totalCommander;
    public static final StringProperty nickForDisplaying = new ReadOnlyStringWrapper("st");;

    public MainWindowController() {
        mainWindowFactory = new MainWindowFactory();
    }

    public MainWindowController setCommander(Commander totalCommander) {
        this.totalCommander = totalCommander;
        return this;
    }

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField serchin_field;

    @FXML
    private Button search_button;

    @FXML
    private ComboBox<?> filter_button;

    @FXML
    private Button insert_button;

    @FXML
    private MenuBar language_menu;

    @FXML
    private Button clear_button;

    @FXML
    private Tab table_selector;

    @FXML
    private TableView<?> tabl;

    @FXML
    private Tab vizualization_selector;

    @FXML
    private Button info_button;

    @FXML
    private TextFlow nick_name_panel;

    @FXML
    private Button sign_out_button;

    @FXML
    void initialize() {
        nick_name_panel.getChildren().add(new Text(nickForDisplaying.get()));
        insert_button.setOnAction(event -> {

            totalCommander.insert();
        });
        clear_button.setOnAction(event -> {
            totalCommander.clear();
        });



    }

    @Override
    public void renderWindow() {
        thisStage.hide();
        thisStage.setResizable(true);
        thisStage.setMaxHeight(645);
        thisStage.setMaxWidth(1178);
        thisStage.setScene(getScene());
        thisStage.show();
    }

    @Override
    public Scene getScene() {
        return mainWindowFactory.createScene();
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

}

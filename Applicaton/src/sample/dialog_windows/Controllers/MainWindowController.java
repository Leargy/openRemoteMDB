package sample.dialog_windows.Controllers;

import java.net.URL;
import java.util.Iterator;
import java.util.ResourceBundle;
import java.util.concurrent.Executor;

import instructions.Decree;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import locale.Localizator;
import sample.buttons.IButton;
import sample.dialog_windows.Commander;
import sample.dialog_windows.Dialog;
import sample.dialog_windows.MainWindowFactory;
import sample.dialog_windows.WindowsFactory;

public class MainWindowController extends Dialog {
    private static IButton buttonActioner;

    private WindowsFactory mainWindowFactory;
    private static Commander totalCommander;
    public static final StringProperty nickForDisplaying = new ReadOnlyStringWrapper("st");
    public static final StringProperty organizationParams = new ReadOnlyStringWrapper("");
    private static boolean[] isOffseted;
    public static InteractWindowController interactWindowController;

    public MainWindowController() {
        isOffseted = new boolean[2];
        mainWindowFactory = new MainWindowFactory();
        interactWindowController = new InteractWindowController().setCommander(totalCommander);
    }

    public void setInteractionPropertys() {

    }

    public MainWindowController setCommander(Commander totalCommander) {
        this.totalCommander = totalCommander;
        return this;
    }

    private java.util.Locale currentLocale = Localizator.DEFAULT;


    @FXML private TextField serchin_field;
    @FXML private Button search_button;
    @FXML private ComboBox<?> filter_button;
    @FXML private Button insert_button;
    @FXML private MenuBar language_menu;
    @FXML private Menu main_lang_choser;
    @FXML private MenuItem main_change_ru;
    @FXML private MenuItem main_change_es;
    @FXML private MenuItem main_change_sl;
    @FXML private MenuItem main_change_uk;
    @FXML private Button clear_button;
    @FXML private Tab table_selector;
    @FXML private TableView<?> tabl;
    @FXML private TableColumn<?, ?> main_table_name;
    @FXML private TableColumn<?, ?> main_table_fullname;
    @FXML private TableColumn<?, ?> main_table_type;
    @FXML private TableColumn<?, ?> main_table_employees;
    @FXML private TableColumn<?, ?> main_table_annual;
    @FXML private TableColumn<?, ?> main_table_zipcode;
    @FXML private TableColumn<?, ?> main_table_creation_date;
    @FXML private TableColumn<?, ?> main_table_owner;
    @FXML private TableColumn<?, ?> main_table_id;
    @FXML private TableColumn<?, ?> main_table_interact;
    @FXML private Tab vizualization_selector;
    @FXML private TextArea info_panel;
    @FXML private TextFlow nick_name_panel;
    @FXML private Button sign_out_button;
    @FXML private Button info_button;


    @FXML
    void initialize() {
        Text nick = new Text(nickForDisplaying.get());
        nick.setFill(Color.WHITE);
//        Iterator iter = Font.getFontNames().iterator();
//        while (iter.hasNext()) {
//            System.out.println(iter.next());
//        }
        nick.setFont(Font.font("Georgia Italic", FontWeight.SEMI_BOLD, 20));
        nick_name_panel.getChildren().add(nick);
        // initialize menu items with images
        ImageView rusView = new ImageView(new Image(getClass().getResource("../../assets/images/ussr.png").toString()));
        main_change_ru.setGraphic(rusView);
        ImageView espView = new ImageView(new Image(getClass().getResource("../../assets/images/panama.gif").toString()));
        main_change_es.setGraphic(espView);
        ImageView slvView = new ImageView(new Image(getClass().getResource("../../assets/images/slovain.gif").toString()));
        main_change_sl.setGraphic(slvView);
        ImageView ukrView = new ImageView(new Image(getClass().getResource("../../assets/images/ukrain.gif").toString()));
        main_change_uk.setGraphic(ukrView);

        insert_button.setOnAction(event -> {
            interactWindowController.renderWindow();
//            try {
//                new Thread(() -> interactWindowController.getData()).join();
//            }catch (InterruptedException ex) {
//                System.err.println(ex.getMessage());
//            }
//            totalCommander.insert(organizationParams.get());
        });
        clear_button.setOnAction(event -> {
            totalCommander.clear();
        });
        info_button.setOnAction(event -> {
            offset(info_button, 160, 1,0);
            offset(info_panel,160,1,1);
        });
        sign_out_button.setOnAction(event -> {
            totalCommander.signOut();
        });

    }

    @Override
    public void renderWindow() {
        thisStage.hide();
        thisStage.setResizable(true);
        thisStage.setMaxHeight(635);
        thisStage.setMaxWidth(1166);
        thisStage.setResizable(false);
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

    private void offset(Control entity, double distance, double speed, int id) {
        double start = entity.getLayoutX();
        if (isOffseted[id] == false) {
            for (double i = start; i < (start + distance); i += speed * Math.abs(1/start)) {
                entity.setLayoutX(i);
            }
            isOffseted[id] = true;
        }
        else {
            for (double i = start; i > (start - distance); i -= speed * Math.abs(1/start) ) {
                entity.setLayoutX(i);
            }
            isOffseted[id] = false;
        }
    }
    @FXML
    public void changeLanguage(ActionEvent actionEvent) {
        Object menuItem = actionEvent.getSource();
        ResourceBundle newBundle = null;
        if (main_change_ru.equals(menuItem))
            currentLocale = Localizator.RUSSIA_USSR;
        else if (main_change_es.equals(menuItem))
            currentLocale = Localizator.SPAIN_PANAMA;
        else if (main_change_sl.equals(menuItem))
            currentLocale = Localizator.SLOVENIAN_SLOVENIA;
        else if (main_change_uk.equals(menuItem))
            currentLocale = Localizator.UKRAINIAN_UKRAINE;
        else currentLocale = Localizator.DEFAULT;
        newBundle = Localizator.changeLocale("locale.table.TableResources", currentLocale);
        updateText2Language(newBundle);
    }

    private void updateText2Language(ResourceBundle bundle) {
        if (bundle == null) return;
        // getting new translation
        String exit = (String) bundle.getObject("Exit");
        String language = (String) bundle.getObject("Language");
        String filter = (String) bundle.getObject("Filter");
        String search = (String) bundle.getObject("Search");
        String insert = (String) bundle.getObject("Insert");
        String clear = (String) bundle.getObject("Clear");
        String info = (String) bundle.getObject("Info");
        String table = (String) bundle.getObject("Table");
        String map = (String) bundle.getObject("Map");
        String name = (String) bundle.getObject("Name");
        String fullname = (String) bundle.getObject("Full Name");
        String type = (String) bundle.getObject("Type");
        String employees = (String) bundle.getObject("Employees Count");
        String annual = (String) bundle.getObject("Annual Turnover");
        String creation = (String) bundle.getObject("Creation date");
        String owner = (String) bundle.getObject("Owner");
        String interact = (String) bundle.getObject("Interaction");
        String noContent = (String) bundle.getObject("No contents in table");
        sign_out_button.setText(exit);
        main_lang_choser.setText(language);
        filter_button.setPromptText(filter);
        search_button.setText(search);
        insert_button.setText(insert);
        clear_button.setText(clear);
        info_button.setText(info);
        table_selector.setText(table);
        vizualization_selector.setText(map);
        main_table_name.setText(name);
        main_table_fullname.setText(fullname);
        main_table_type.setText(type);
        main_table_employees.setText(employees);
        main_table_annual.setText(annual);
        main_table_creation_date.setText(creation);
        main_table_owner.setText(owner);
        main_table_interact.setText(interact);
        tabl.setPlaceholder(new Label(noContent));
        tabl.refresh();
    }
}

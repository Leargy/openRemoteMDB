package sample.dialog_windows.Controllers;

import java.net.URL;
import java.sql.Time;
import java.util.*;
import java.util.concurrent.Executor;

import com.sun.scenario.effect.LockableResource;
import instructions.Decree;
import javafx.application.Platform;
import javafx.beans.property.*;
import javafx.beans.value.ObservableSetValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Background;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Callback;
import locale.Localizator;
import organization.Organization;
import organization.OrganizationWithUId;
import sample.Main;
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
    private static InteractWindowController interactWindowController;
    private static ObservableList<OrganizationWithUId> concatenationList = FXCollections.observableArrayList();
    private static final ArrayList<Button> tableButtons = new ArrayList<>();
    private static final ObservableList<Label> onlineUsers = FXCollections.observableArrayList();
    private static int numberOfUsers = 1;
    private static boolean userCollectionModified = false;
//    private static boolean firstLoad = true;
    private static final Text info = new Text();

    private static Timer tableTimer = new Timer();
    private static Timer infoTimer = new Timer();
    private static TimerTask tableTimerTask ;
    private static TimerTask infoTimerTask ;


    private StringProperty name = new SimpleStringProperty();
    private StringProperty fullName = new SimpleStringProperty();
    private StringProperty zipCod = new SimpleStringProperty();
    private StringProperty type = new SimpleStringProperty();
    private StringProperty owner = new SimpleStringProperty();
    private StringProperty date = new SimpleStringProperty();
    private FloatProperty annualTurnover = new SimpleFloatProperty();
    private IntegerProperty employeesCount = new SimpleIntegerProperty();
    private IntegerProperty id = new SimpleIntegerProperty();

    public MainWindowController() {
        isOffseted = new boolean[4];
        mainWindowFactory = new MainWindowFactory();
        interactWindowController = new InteractWindowController().setCommander(totalCommander);
//        onlineUsers.add(nickForDisplaying.get());
//        System.out.println(onlineUsers.size());
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
    @FXML private TableView<OrganizationWithUId> tabl;
    @FXML private TableColumn<OrganizationWithUId, String> main_table_name;
    @FXML private TableColumn<OrganizationWithUId, String> main_table_fullname;
    @FXML private TableColumn<OrganizationWithUId, String> main_table_type;
    @FXML private TableColumn<OrganizationWithUId, Integer> main_table_employees;
    @FXML private TableColumn<OrganizationWithUId, Float> main_table_annual;
    @FXML private TableColumn<OrganizationWithUId, String> main_table_zipcode;
    @FXML private TableColumn<OrganizationWithUId, String> main_table_creation_date;
    @FXML private TableColumn<OrganizationWithUId, String> main_table_owner;
    @FXML private TableColumn<OrganizationWithUId, Integer> main_table_id;
    @FXML private TableColumn<OrganizationWithUId, Void> main_table_interact;
    @FXML private Tab vizualization_selector;
    @FXML private Label info_panel;
    @FXML private TextFlow nick_name_panel;
    @FXML private Button sign_out_button;
    @FXML private Button info_button;
    @FXML private Button online_button;
    @FXML private VBox online_panel;

    @FXML
    void initialize() {
        addOnlineUser(nickForDisplaying.get());
//        online_panel.setText(onlineUsers.getText());
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
            interactWindowController.setRowOrganization(null);
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
            offset(info_button, 569, 1,0,"X");
            offset(info_panel,569,1,1,"X");
        });
        online_button.setOnAction(event -> {
            offset(online_button, 150, 1,2,"Y");
            offset(online_panel,150,1,3, "Y");
        });

        sign_out_button.setOnAction(event -> {
//            firstLoad = true;
            tableTimerTask.cancel();
            infoTimerTask.cancel();
//            timer.cancel();
            tabl.getItems().clear();
            organizationsToAdd.clear();
            totalCommander.signOut();
        });

        tabl.refresh();
        main_table_name.setCellValueFactory(celldata -> {
            name.setValue(celldata.getValue().getName());
            return name;
        });
        main_table_fullname.setCellValueFactory(celldata -> {
            fullName.setValue(celldata.getValue().getFullname());
            return fullName;
        });
        main_table_type.setCellValueFactory(celldata -> {
            type.setValue(celldata.getValue().getType().toString());
            return type;
        });
        main_table_employees.setCellValueFactory(celldata -> {
            employeesCount.setValue(celldata.getValue().getEmployeesCount());
            return employeesCount.asObject();
        });
        main_table_annual.setCellValueFactory(celldata -> {
            annualTurnover.setValue(celldata.getValue().getAnnualTurnover());
            return annualTurnover.asObject();
        });
        main_table_zipcode.setCellValueFactory(celldata -> {
            zipCod.setValue(celldata.getValue().getAddress().getZipCode());
            return zipCod;
        });
        main_table_creation_date.setCellValueFactory(celldata -> {
            date.setValue(celldata.getValue().getCreationDate().toString());
            return date;
        });
        main_table_owner.setCellValueFactory(celldata -> {
            owner.setValue(celldata.getValue().getUserLogin());
            return owner;
        });
        main_table_id.setCellValueFactory(celldata -> {
            id.setValue(celldata.getValue().getKey());
            return id.asObject();
        });


        tableTimerTask = new TimerTask() {
            @Override
            public void run() {
//                if (hasChanges) {
                    synchronized (organizationsToAdd) {
//                        System.out.println(organizationsToAdd);
                        tabl.getItems().clear();
                        tabl.getItems().addAll(organizationsToAdd);
                        tabl.refresh();
                        hasChanges = false;
                    }
//                }
            }
        };
        infoTimerTask = new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    if (userCollectionModified) {
                        online_panel.getChildren().clear();
                        online_panel.getChildren().addAll(onlineUsers);
                    }
                    totalCommander.info();
                    info_panel.setText(info.getText());
                });
            }
        };

        tableTimer.schedule(tableTimerTask,100L,200L);
        infoTimer.schedule(infoTimerTask,500L,2000L);



        addInteructionButton();
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
    public void setInfo(String info) {
            this.info.setText("\t" + info);
//        info_panel.setText(info);
    }

    @Override
    public void initAlertBox(String alertMessage) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Authorization");
        alert.setHeaderText(null);
        alert.setContentText(alertMessage);
        alert.showAndWait();
    }

    public void addOnlineUser(String userName) {
        onlineUsers.add(prepareLabel(userName));
        userCollectionModified = true;
    }

    public void removeOnlineUser(String userName) {
        onlineUsers.removeAll(prepareLabel(userName));
        userCollectionModified = true;
    }

    private void offset(Node entity, double distance, double speed, int id, String vector) {
        switch (vector.toUpperCase()) {
            case "X": {
                double start = entity.getLayoutX();
                if (isOffseted[id] == false) {
                    for (double i = start; i < (start + distance); i += speed * Math.abs(1 / start)) {
                        entity.setLayoutX(i);
                    }
                    isOffseted[id] = true;
                } else {
                    for (double i = start; i > (start - distance); i -= speed * Math.abs(1 / start)) {
                        entity.setLayoutX(i);
                    }
                    isOffseted[id] = false;
                }
                break;
            }
            case "Y": {
                double start = entity.getLayoutY();
                if (isOffseted[id] == false) {
                    for (double i = start; i > (start - distance); i -= speed ) {
                        entity.setLayoutY(i);
                    }
                    isOffseted[id] = true;
                } else {
                    for (double i = start; i < (start + distance); i += speed) {
                        entity.setLayoutY(i);
                    }
                    isOffseted[id] = false;
                }
                break;
            }
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
        String editingButton = (String) bundle.getObject("Edit");
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

    public void addInteructionButton() {
        Callback<TableColumn<OrganizationWithUId, Void>, TableCell<OrganizationWithUId, Void>> cellFactory = new Callback<TableColumn<OrganizationWithUId, Void>, TableCell<OrganizationWithUId, Void>>() {
            @Override
            public TableCell<OrganizationWithUId, Void> call(final TableColumn<OrganizationWithUId, Void> param) {
                final TableCell<OrganizationWithUId, Void> cell = new TableCell<OrganizationWithUId, Void>() {

                    private final Button btn = new Button((String) Localizator.changeLocale("locale.table.TableResources", currentLocale).getObject("Edit"));
                    {
                        btn.setOnAction((ActionEvent event) -> {
                            interactWindowController.setRowOrganization(tabl.getColumns().get(super.getIndex()).getTableView().getItems().get(super.getIndex()).getOrganization());
                            interactWindowController.renderWindow();
                        });
                        btn.setStyle("-fx-background-color: #73ACE6; -fx-border-color: #8F8F8F");
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(btn);
                        }
                    }
                };
                return cell;
            }
        };

        main_table_interact.setCellFactory(cellFactory);
    }

    private Label prepareLabel(String textForLabel) {
        Label label = new Label();
        label.setMinWidth(169);
        label.setMinHeight(32);
        label.setAlignment(Pos.CENTER);
        label.setFont(Font.font("Georgia Bold",15));
        label.setText(textForLabel);
        return label;
    }
}

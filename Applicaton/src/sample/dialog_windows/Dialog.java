package sample.dialog_windows;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.stage.Stage;
import organization.OrganizationWithUId;

import java.util.ArrayList;

public abstract class Dialog {
    public static final Stage thisStage = new Stage();
    static {
        thisStage.setTitle("RemoteMDB");
        thisStage.setMaxWidth(340.0);
        thisStage.setMaxHeight(430.0);
        thisStage.setResizable(false);
    }
    protected static ObservableList<OrganizationWithUId> organizationsToAdd = null;

    public abstract void renderWindow();

    public abstract Scene getScene();

    public abstract String getData();

    public abstract void initAlertBox(String alertMessage);

    public void insertOrganizations(ArrayList<OrganizationWithUId> tempAddition) {
//        System.out.println(Thread.currentThread().getName());
        System.out.println(tempAddition.size());
//        ObservableList<OrganizationWithUId> organizationWithUIds = FXCollections.observableArrayList(tempAddition);
        organizationsToAdd = FXCollections.observableArrayList(tempAddition);
//        tabl.refresh();
//        tabl.setItems(organizationWithUIds);
// TODO: дописать инсерт и другие команды, добавить обработку отключающегося и недоступного сервера, добавить анимацию, добавить фильтрацию
    }
}

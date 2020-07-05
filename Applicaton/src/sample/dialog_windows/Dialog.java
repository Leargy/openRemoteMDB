package sample.dialog_windows;

import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.stage.Stage;
import organization.Organization;
import organization.OrganizationWithUId;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public abstract class Dialog {
    public static final Stage thisStage = new Stage();
    static {
        thisStage.setTitle("RemoteMDB");
        thisStage.setMaxWidth(340.0);
        thisStage.setMaxHeight(430.0);
        thisStage.setResizable(false);
    }
    protected static volatile ObservableList<OrganizationWithUId> organizationsToAdd = FXCollections.observableArrayList();
    protected static volatile boolean  hasChanges = false;

    public abstract void renderWindow();

    public abstract Scene getScene();

    public abstract void setInfo(String info);

    public abstract void initAlertBox(String alertMessage);

    public void insertOrganizations(ArrayList<OrganizationWithUId> tempAddition) {
        synchronized (organizationsToAdd) {
            organizationsToAdd.addAll(tempAddition);
            hasChanges = true;
        }
    }

    public abstract void changingSignal ();

    public void clearOrganizations(ArrayList<OrganizationWithUId> tempAddition) {
        synchronized (organizationsToAdd) {
            List result = Arrays.asList(organizationsToAdd.stream().filter((tempOrg) -> (tempOrg.USER_LOGIN.equals(tempAddition.get(0).USER_LOGIN))).toArray());
            organizationsToAdd.removeAll(result);
            hasChanges = true;
        }
    }

    public void updateOrganizations(ArrayList<OrganizationWithUId> tempAddition) {
        synchronized (organizationsToAdd) {
            List result = Arrays.asList(organizationsToAdd.stream().filter((tempOrg) -> (tempOrg.getOrganization().id == tempAddition.get(0).getOrganization().id)).toArray());
            organizationsToAdd.removeAll(result);
            organizationsToAdd.add(tempAddition.get(0));
            hasChanges = true;
        }
    }

    public void removeOrganization(ArrayList<OrganizationWithUId> tempAddition) {
        synchronized (organizationsToAdd) {
            List result = Arrays.asList(organizationsToAdd.stream().filter((tempOrg) -> (tempOrg.getOrganization().id == tempAddition.get(0).getOrganization().id)).toArray());
            System.out.println(result.get(0));
//            organizationsToAdd.remove(result);
            organizationsToAdd.removeAll(result);
            hasChanges = true;
        }
    }
}

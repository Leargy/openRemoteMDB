package sample.dialog_windows;

import sample.dialog_windows.Controllers.MainWindowController;
import sample.dialog_windows.communication.ApplicationParcel;
import sample.dialog_windows.communication.Mediating;

public class WindowOwner {
    private volatile Dialog tempWindow;
    public final Mediating mediator;

    public WindowOwner(Mediating mediator) {
        this.mediator = mediator;
    }

    public synchronized void setTempWindow(Dialog tempWindow) {
        this.tempWindow = tempWindow;
    }

    public  void renderTempWindow() {
        tempWindow.renderWindow();
    }

    public void initAlertBox(String message) { tempWindow.initAlertBox(message);}

    public synchronized void insertInTable(ApplicationParcel applicationParcel) {
        tempWindow.insertOrganizations(applicationParcel.getOrganizationArrayList());
    }
    public synchronized void updateTable(ApplicationParcel applicationParcel) {
        tempWindow.updateOrganizations(applicationParcel.getOrganizationArrayList());
    }
    public synchronized void removeFromTable(ApplicationParcel applicationParcel) {
        tempWindow.removeOrganization(applicationParcel.getOrganizationArrayList());
    }
    public synchronized void clearTable(ApplicationParcel applicationParcel) {
        tempWindow.clearOrganizations(applicationParcel.getOrganizationArrayList());
    }

    public void setInfo(String info) {
        tempWindow.setInfo(info);
    }
    public void setConnectedUser(String rawString) {
//        for(int i = 0; i < 15; i++) {
//            ((MainWindowController)tempWindow).addOnlineUser("sucker");
//        }
        String[] rawUsers = rawString.split(" ");
        if (rawUsers.length > 1) {
            for (int i = 0; i < rawUsers.length; i++) {
                ((MainWindowController)tempWindow).addOnlineUser(rawUsers[i]);
            }
        }else ((MainWindowController)tempWindow).addOnlineUser(rawUsers[0]);
    }
    public void removeDisconnectedUser(String rawString) {
        ((MainWindowController)tempWindow).removeOnlineUser(rawString.split(" ")[0]);
    }
}

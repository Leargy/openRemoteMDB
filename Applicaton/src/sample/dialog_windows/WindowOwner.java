package sample.dialog_windows;

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
//        (MainWindowController)tempWindow
    }
    public synchronized void removeFromTable(ApplicationParcel applicationParcel) {
//        (MainWindowController)tempWindow
    }
    public synchronized void clearTable(ApplicationParcel applicationParcel) {
//        (MainWindowController)tempWindow
    }
}

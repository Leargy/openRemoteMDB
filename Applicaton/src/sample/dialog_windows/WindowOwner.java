package sample.dialog_windows;

import sample.dialog_windows.communication.Mediating;

public class WindowOwner {
    private Dialog tempWindow;
    public final Mediating mediator;

    public WindowOwner(Mediating mediator) {
        this.mediator = mediator;
    }

    public void setTempWindow(Dialog tempWindow) {
        this.tempWindow = tempWindow;
    }

    public void renderTempWindow() {
        tempWindow.renderWindow();
    }

    public void initAlertBox(String message) { tempWindow.initAlertBox(message);}
}

package sample.dialog_windows;

import javafx.scene.Scene;
import javafx.stage.Stage;

public abstract class Dialog {
    protected static Stage thisStage = new Stage();
    static {
        thisStage.setTitle("RemoteMDB");
        thisStage.setResizable(false);
    }

    public abstract void renderWindow();

    public abstract Scene getScene();

    public abstract String getData();

    public abstract void initAlertBox(String alertMessage);
}

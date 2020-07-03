package sample.dialog_windows;

import javafx.scene.Scene;
import javafx.stage.Stage;

public abstract class Dialog {
    public static final Stage thisStage = new Stage();
    static {
        thisStage.setTitle("RemoteMDB");
        thisStage.setMaxWidth(340.0);
        thisStage.setMaxHeight(430.0);
        thisStage.setResizable(false);
    }

    public abstract void renderWindow();

    public abstract Scene getScene();

    public abstract String getData();

    public abstract void initAlertBox(String alertMessage);
}

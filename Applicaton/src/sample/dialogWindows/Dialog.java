package sample.dialogWindows;

import javafx.stage.Stage;
import sample.buttons.IButton;

import java.io.IOException;

public abstract class Dialog {
    protected static Stage thisStage;
    public abstract void renderWindow() throws IOException;

    public abstract IButton getButton();
}

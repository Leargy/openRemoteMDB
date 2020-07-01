package sample.buttons;

import javafx.scene.control.TextField;
import sample.dialogWindows.LogInWindow;

import java.io.IOException;

public class ConnectionIButton implements IButton {
    private LogInWindow logInWindow;
    private String ip;
    private String port;

    public ConnectionIButton() {
        logInWindow = new LogInWindow();
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public void setPort(String port) {
        this.port = port;
    }

    @Override
    public void click() {
//        try {
//            logInWindow.renderWindow();
//        }catch (IOException ex) {
//            System.err.println(ex.getMessage());
//        }
    }
}

package sample.buttons;


import sample.dialog_windows.Commander;
import sample.dialog_windows.communication.ApplicationParcel;

public class ConnectionIButton implements IButton {
    private Commander commander;
    private String ip;
    private String port;

    public ConnectionIButton(Commander commander) {
        this.commander = commander;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public void setPort(String port) {
        this.port = port;
    }

    @Override
    public void click() {
        commander.setConnection(new ApplicationParcel("ip=" + ip + " " + "port=" + port));
    }
}

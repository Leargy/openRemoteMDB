package sample.buttons;

import sample.dialog_windows.Commander;

public class BackButton implements IButton {
    private Commander commander;

    public BackButton(Commander commander) {
        this.commander = commander;
    }

    @Override
    public void click() {

    }
}

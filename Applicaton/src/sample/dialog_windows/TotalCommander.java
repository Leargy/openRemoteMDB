package sample.dialog_windows;

import communication.Segment;
import instructions.rotten.RawDecree;
import instructions.rotten.base.RawSignIn;
import instructions.rotten.base.RawSignOut;
import instructions.rotten.base.RawSignUp;
import sample.dialog_windows.communication.Component;
import sample.dialog_windows.communication.Mediating;
import sample.dialog_windows.communication.ApplicationParcel;
import sample.dialog_windows.communication.enum_section.Markers;
import sample.dialog_windows.handlers.ConnectionHandler;
import sample.dialog_windows.handlers.Handler;
import sample.dialog_windows.handlers.exceptions.*;

public class TotalCommander implements Commander, Component {
    private Handler connectionHandler;
    public final Mediating mediator;

    public TotalCommander(Mediating mediator) {
        this.mediator = mediator;
        connectionHandler = new ConnectionHandler();
    }

    @Override
    public void setConnection(ApplicationParcel applicationParcel) {
        try {
            applicationParcel = connectionHandler.handle(applicationParcel);
        }catch (CommonHandlerException ex) {
            mediator.notify(this,new ApplicationParcel(ex.getMessage(),Markers.SENDALLERT));
            return;
        }
        mediator.notify(this, new ApplicationParcel(applicationParcel.getMessage(), Markers.SETCONNECTION));
    }

    @Override
    public void back() {
        mediator.notify(this, new ApplicationParcel(Markers.RESETCONNECCTION));
        mediator.notify(this, new ApplicationParcel(Markers.PRIVIOUSSTAGE));
    }

    @Override
    public void signIn(String login, String password) {
        if (login.equals("") || password.equals("")) {
            mediator.notify(this,new ApplicationParcel("",Markers.SENDALLERT));
        }
        ApplicationParcel applicationParcel = new ApplicationParcel(RawSignIn.NAME + " " + login + " " + password, Markers.SENDCOMMAND);
        mediator.notify(this,applicationParcel);
    }


    @Override
    public void signOut() {
        ApplicationParcel applicationParcel = new ApplicationParcel(RawSignOut.NAME , Markers.SENDCOMMAND);
        mediator.notify(this,applicationParcel);
    }

    @Override
    public void signUp(String login, String password) {
        ApplicationParcel applicationParcel = new ApplicationParcel(RawSignUp.NAME + " " + login + " " + password, Markers.SENDCOMMAND);
        mediator.notify(this,applicationParcel);
    }


    @Override
    public void clear() {

    }

    @Override
    public void insert() {

    }

    @Override
    public void update() {

    }

    @Override
    public void info() {

    }
}

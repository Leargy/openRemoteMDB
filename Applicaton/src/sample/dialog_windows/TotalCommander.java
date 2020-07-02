package sample.dialog_windows;

import sample.assets.exceptions.CommonHandlerException;
import sample.dialog_windows.communication.Component;
import sample.dialog_windows.communication.Mediating;
import sample.dialog_windows.communication.Parcel;
import sample.dialog_windows.communication.enum_section.Markers;
import sample.dialog_windows.handlers.ConnectionHandler;
import sample.dialog_windows.handlers.Handler;

public class TotalCommander implements Commander, Component {
    private Handler connectionHandler;
    public final Mediating mediator;

    public TotalCommander(Mediating mediator) {
        this.mediator = mediator;
        connectionHandler = new ConnectionHandler();
    }

    @Override
    public void setConnection(Parcel parcel) {
        try {
            connectionHandler.handle(parcel);
        }catch (CommonHandlerException ex) {
            mediator.notify(this,new Parcel(ex.getMessage(),Markers.SENDALLERT));
            return;
        }
        mediator.notify(this, new Parcel(Markers.NEXTSTAGE));
    }

    @Override
    public void signIn() {

    }

    @Override
    public void signOut() {

    }

    @Override
    public void signUp() {

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

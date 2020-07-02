package sample.dialog_windows.handlers;

import sample.assets.exceptions.CommonHandlerException;
import sample.dialog_windows.communication.Parcel;

public interface Handler {
    void setNext(Handler nextHandler);
    boolean handle(Parcel parcel) throws CommonHandlerException;
}

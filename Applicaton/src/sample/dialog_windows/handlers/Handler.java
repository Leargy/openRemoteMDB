package sample.dialog_windows.handlers;

import sample.dialog_windows.handlers.exceptions.*;
import sample.dialog_windows.communication.ApplicationParcel;

public interface Handler {
    void setNext(Handler nextHandler);
    ApplicationParcel handle(ApplicationParcel applicationParcel) throws CommonHandlerException;
}

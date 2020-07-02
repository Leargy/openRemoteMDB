package sample.dialog_windows.handlers;

import sample.dialog_windows.communication.ApplicationParcel;
import sample.dialog_windows.dataSection.ConnectionDataParser;
import sample.dialog_windows.dataSection.Parsing;
import sample.dialog_windows.handlers.exceptions.*;

public class ConnectionHandler implements Handler {
    private Handler nextHandler;
    private Parsing ipAndPortParser;

    public ConnectionHandler() {
        ipAndPortParser = new ConnectionDataParser();
    }

    @Override
    public void setNext(Handler nextHandler) {
        this.nextHandler = nextHandler;
    }

    @Override
    public ApplicationParcel handle(ApplicationParcel applicationParcel) throws CommonHandlerException {
        try {
            applicationParcel = ipAndPortParser.pars(applicationParcel);
        }catch (ParsingException ex) {
            throw new CommonHandlerException(ex.getMessage());
        }
        return applicationParcel;
    }
}

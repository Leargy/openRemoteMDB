package sample.dialog_windows.handlers;

import sample.assets.exceptions.CommonHandlerException;
import sample.assets.exceptions.ParsingException;
import sample.dialog_windows.communication.Parcel;
import sample.dialog_windows.dataSection.ConnectionDataParser;
import sample.dialog_windows.dataSection.Parsing;

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
    public boolean handle(Parcel parcel) throws CommonHandlerException {
        try {
            ipAndPortParser.pars(parcel);
        }catch (ParsingException ex) {
            throw new CommonHandlerException(ex.getMessage());
        }
        return true;
    }
}

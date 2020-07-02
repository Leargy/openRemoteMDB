package sample.dialog_windows.dataSection;

import sample.dialog_windows.handlers.exceptions.*;
import sample.dialog_windows.communication.ApplicationParcel;

public interface Parsing {
    ApplicationParcel pars(ApplicationParcel applicationParcel) throws ParsingException;
}

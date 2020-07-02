package sample.dialog_windows.dataSection;

import sample.assets.exceptions.ParsingException;
import sample.dialog_windows.communication.Parcel;

public interface Parsing {
    void pars(Parcel parcel) throws ParsingException;
}

package sample.dialog_windows.communication;

import instructions.rotten.RawDecree;
import sample.dialog_windows.communication.enum_section.Markers;

public class ApplicationParcel implements Parcel{
    private String message;
    private Markers marker;
    private RawDecree rawCommand;

    public ApplicationParcel(String message) {
        this.message = message;
    }

    public ApplicationParcel(Markers marker) {
        this.marker = marker;
    }

    public ApplicationParcel(String message, Markers marker) {
        this(message);
        this.marker = marker;
    }

    public void setRawCommand(RawDecree rawCommand) {
        this.rawCommand = rawCommand;
    }

    public Markers getMarker() {
        return marker;
    }

    public String getMessage() {
        return message;
    }
}

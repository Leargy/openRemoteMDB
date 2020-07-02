package sample.dialog_windows.communication;

import sample.dialog_windows.communication.enum_section.Markers;

public class Parcel {
    private String message;
    private Markers marker;

    public Parcel(String message) {
        this.message = message;
    }

    public Parcel(Markers marker) {
        this.marker = marker;
    }

    public Parcel(String message, Markers marker) {
        this(message);
        this.marker = marker;
    }

    public Markers getMarker() {
        return marker;
    }

    public String getMessage() {
        return message;
    }
}

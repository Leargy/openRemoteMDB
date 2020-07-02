package sample.dialog_windows.communication;

import communication.Segment;
import data_section.enumSection.Markers;

public class BorderConverter implements Converting {
    private static BorderConverter borderConverter;
    private BorderConverter() { }

    public static BorderConverter getInstance() {
        if (borderConverter == null) {
            synchronized (BorderConverter.class) {
                if (borderConverter == null) {
                    borderConverter = new BorderConverter();
                }
            }
        }
        return borderConverter;
    }

    @Override
    public Segment convertToClientsPackage(ApplicationParcel parcel) {
        Segment segment = new Segment(null);
        try {
            segment.setStringData(parcel.getMessage().split(" "));
        }catch (NullPointerException ex) { /*NOPE*/ }
        switch (parcel.getMarker()) {
            case NEXTSTAGE:
            case SENDALLERT:
            case PRIVIOUSSTAGE:
            case SENDCOMMAND: segment.setMarker(Markers.WRITE);
                break;
            case RESETCONNECCTION: segment.setMarker(Markers.INTERRUPTED);
                break;
        }
        return segment;
    }

    @Override
    public ApplicationParcel convertToApplicationPackage(Segment segment) {
        ApplicationParcel applicationParcel = null;
        switch (segment.getMarker()) {
            case INTERRUPTED:
            case WRITE:
            case WAIKUP:
            case BADINPUTCONDITION: {
                String[] segregatedMessage = segment.getClientPackage().getReport().Message().split(":");
                applicationParcel = new ApplicationParcel(segregatedMessage[1], sample.dialog_windows.communication.enum_section.Markers.SENDALLERT);
                break;
            }
            case GOODINPUTCONDITION: applicationParcel = new ApplicationParcel(sample.dialog_windows.communication.enum_section.Markers.NEXTSTAGE);
                break;
        }
        return applicationParcel;
    }
}

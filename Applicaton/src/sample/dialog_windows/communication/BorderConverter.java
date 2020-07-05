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
            case STOP: segment.setMarker(Markers.STOP);
                break;
            case SENDCOMMAND: segment.setMarker(Markers.WRITE);
                break;
            case RESETCONNECCTION: segment.setMarker(Markers.INTERRUPTED);
                break;
        }
        return segment;
    }

    @Override
    public ApplicationParcel convertToApplicationPackage(Segment segment) {
        boolean withClientPackage = false;
        if (segment.getClientPackage() != null) {
            withClientPackage = true;
        }

        ApplicationParcel applicationParcel = null;
        switch (segment.getMarker()) {
            case USER_CONNECTED:applicationParcel = new ApplicationParcel(segment.getClientPackage().getReport().Message(), sample.dialog_windows.communication.enum_section.Markers.USER_CONNECTED);
                break;
            case USER_DISCONNECTED: applicationParcel = new ApplicationParcel(segment.getClientPackage().getReport().Message(), sample.dialog_windows.communication.enum_section.Markers.USER_DISCONNECTED);
                break;
            case INFO: {
                applicationParcel = new ApplicationParcel(segment.getClientPackage().getReport().Message(), sample.dialog_windows.communication.enum_section.Markers.INFO);
                break;
            }
            case CLEAR: {
                applicationParcel = new ApplicationParcel("", sample.dialog_windows.communication.enum_section.Markers.CLEAR);
                applicationParcel.setOrganizationArrayList(segment.getClientPackage().getReport().getOrganizations());
                break;
            }
            case UPDATE: {
                applicationParcel = new ApplicationParcel("", sample.dialog_windows.communication.enum_section.Markers.UPDATE);
                applicationParcel.setOrganizationArrayList(segment.getClientPackage().getReport().getOrganizations());
                break;
            }
            case INSERT: {
                applicationParcel = new ApplicationParcel("", sample.dialog_windows.communication.enum_section.Markers.INSERT);
                applicationParcel.setOrganizationArrayList(segment.getClientPackage().getReport().getOrganizations());
                break;
            }
            case REMOVE: {
                applicationParcel = new ApplicationParcel("", sample.dialog_windows.communication.enum_section.Markers.REMOVE);
                applicationParcel.setOrganizationArrayList(segment.getClientPackage().getReport().getOrganizations());
                break;
            }
            case BADINPUTCONDITION: {
                if (withClientPackage) {
                    String[] segregatedMessage = segment.getClientPackage().getReport().Message().split(":");
                    applicationParcel = new ApplicationParcel(segregatedMessage[1], sample.dialog_windows.communication.enum_section.Markers.SENDALLERT);
                }else {
                    applicationParcel = new ApplicationParcel(segment.getStringData()[0], sample.dialog_windows.communication.enum_section.Markers.SENDALLERT);
                }
                break;
            }
            case GOODINPUTCONDITION:
                break;
            case CONFIRMING: {
                if (withClientPackage){
                    if (!segment.getClientPackage().getReport().getIsConfirmed() && segment.getClientPackage().getReport().isSuccessful()) {
                        applicationParcel = new ApplicationParcel(sample.dialog_windows.communication.enum_section.Markers.PRIVIOUSSTAGE);
                    }else if (!segment.getClientPackage().getReport().isSuccessful()) {
                        break;
//                        String[] segregatedMessage = segment.getClientPackage().getReport().Message().split(":");
//                        applicationParcel = new ApplicationParcel(segregatedMessage[1], sample.dialog_windows.communication.enum_section.Markers.SENDALLERT);
                    } else applicationParcel = new ApplicationParcel(sample.dialog_windows.communication.enum_section.Markers.NEXTSTAGE);
                }else {
                    applicationParcel = new ApplicationParcel(sample.dialog_windows.communication.enum_section.Markers.NEXTSTAGE);
                }
                break;
            }
        }
        return applicationParcel;
    }
}

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
            case BADINPUTCONDITION: {
                if (withClientPackage) {
//                    System.out.println("gege");
                    String[] segregatedMessage = segment.getClientPackage().getReport().Message().split(":");
                    applicationParcel = new ApplicationParcel(segregatedMessage[1], sample.dialog_windows.communication.enum_section.Markers.SENDALLERT);
                }else {
//                    System.out.println("gege");
                    applicationParcel = new ApplicationParcel(segment.getStringData()[0], sample.dialog_windows.communication.enum_section.Markers.SENDALLERT);
                }
                break;
            }
            case GOODINPUTCONDITION:
                break;
            case CONFIRMING: {
                if (withClientPackage){
//                    System.out.println("gege");
                    if (!segment.getClientPackage().getReport().getIsConfirmed() && segment.getClientPackage().getReport().isSuccessful()) {
                        applicationParcel = new ApplicationParcel(sample.dialog_windows.communication.enum_section.Markers.PRIVIOUSSTAGE);
                    }else if (!segment.getClientPackage().getReport().isSuccessful()) {
                        break;
//                        System.out.println("gege");
//                        String[] segregatedMessage = segment.getClientPackage().getReport().Message().split(":");
//                        applicationParcel = new ApplicationParcel(segregatedMessage[1], sample.dialog_windows.communication.enum_section.Markers.SENDALLERT);
                    } else applicationParcel = new ApplicationParcel(sample.dialog_windows.communication.enum_section.Markers.NEXTSTAGE);
                }else {
//                    System.out.println("gege");
                    applicationParcel = new ApplicationParcel(sample.dialog_windows.communication.enum_section.Markers.NEXTSTAGE);
                }
                break;
            }
        }
        return applicationParcel;
    }
}

package sample.dialog_windows.communication;

import communication.Segment;

public interface Converting {
    Segment convertToClientsPackage(ApplicationParcel parcel);
    Parcel convertToApplicationPackage(Segment segment);
}

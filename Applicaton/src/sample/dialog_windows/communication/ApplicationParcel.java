package sample.dialog_windows.communication;

import instructions.rotten.RawDecree;
import organization.Organization;
import organization.OrganizationWithUId;
import sample.dialog_windows.communication.enum_section.Markers;

import java.util.ArrayList;

public class ApplicationParcel implements Parcel{
    private String message;
    private Markers marker;
    private RawDecree rawCommand;
    private ArrayList<OrganizationWithUId> organizationArrayList;

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

    public ArrayList<OrganizationWithUId> getOrganizationArrayList() {
        return organizationArrayList;
    }

    public void setOrganizationArrayList(ArrayList<OrganizationWithUId> organizationArrayList) {
        this.organizationArrayList = organizationArrayList;
    }
}

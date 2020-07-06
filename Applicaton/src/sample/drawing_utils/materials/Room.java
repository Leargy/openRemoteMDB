package sample.drawing_utils.materials;

import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import organization.OrganizationWithUId;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class Room {
    private final OrganizationWithUId LINK;
    public final RoomType TYPE;
    private final Window[] WINDOWS;
    private final Door[] DOORS;
    private final Beam[] VERTICAL_BEAMS;
    private final Beam[] HORIZONTAL_BEAMS;
    private final Flag[] FLAGS;
    private Facade FACADE;

    public Room(OrganizationWithUId link, RoomType type) {
        LINK = link;
        TYPE = type;
        WINDOWS = new Window[TYPE.WINDOW_COUNT];
        DOORS = new Door[TYPE.DOOR_COUNT];
        VERTICAL_BEAMS = new Beam[TYPE.VERTICAL_BEAM_COUNT];
        HORIZONTAL_BEAMS = new Beam[TYPE.HORIZONTAL_BEAM_COUNT];
        FLAGS = new Flag[TYPE.FLAG_COUNT];
    }

    public Room setLink(OrganizationWithUId link) {
        final Room newly = new Room(link, TYPE);
        newly.installFacade(FACADE);
        for (int i = 0; i < WINDOWS.length; ++i)
            newly.installWindow(i, WINDOWS[i]);
        for (int i = 0; i < DOORS.length; ++i)
            newly.installDoor(i, DOORS[i]);
        for (int i = 0; i < VERTICAL_BEAMS.length; ++i)
            newly.installVBeam(i, VERTICAL_BEAMS[i]);
        for (int i = 0; i < HORIZONTAL_BEAMS.length; ++i)
            newly.installHBeam(i, HORIZONTAL_BEAMS[i]);
        return newly;
    }

    public OrganizationWithUId getLink() {
        return LINK;
    }

    public void installFacade(Facade facade) {
        FACADE = facade;
    }

    public void installWindow(int position, Window window) {
        WINDOWS[position] = window;
    }

    public void installDoor(int position, Door door) {
        DOORS[position] = door;
    }

    public void installVBeam(int position, Beam beam) {
        VERTICAL_BEAMS[position] = beam;
    }

    public void installHBeam(int position, Beam beam) {
        HORIZONTAL_BEAMS[position] = beam;
    }

    public void installFlag(int position, Flag flag) {
        FLAGS[position] = flag;
    }

    public Node[] getAllBuildingMaterials() {
        Node[] nodes = new Node[WINDOWS.length + DOORS.length + VERTICAL_BEAMS.length + HORIZONTAL_BEAMS.length + FLAGS.length + 1];
        nodes[0] = FACADE;
        System.arraycopy(VERTICAL_BEAMS, 0,
                nodes, 1, VERTICAL_BEAMS.length);
        System.arraycopy(HORIZONTAL_BEAMS, 0,
                nodes, 1 + VERTICAL_BEAMS.length, HORIZONTAL_BEAMS.length);
        System.arraycopy(DOORS, 0,
                nodes, 1 + HORIZONTAL_BEAMS.length +  VERTICAL_BEAMS.length, DOORS.length);
        System.arraycopy(WINDOWS, 0, nodes, 1 + HORIZONTAL_BEAMS.length +  VERTICAL_BEAMS.length + DOORS.length, WINDOWS.length);
        System.arraycopy(FLAGS, 0,
                nodes, WINDOWS.length + 1 + HORIZONTAL_BEAMS.length +  VERTICAL_BEAMS.length + DOORS.length, FLAGS.length);
        return nodes;
    }

    public void turnOnLight() {
        changeWindowColour(Color.GHOSTWHITE);
    }

    public void turnOffLight() {
        changeWindowColour(Color.BLACK);
    }

    private void changeWindowColour(Paint colour) {
        Arrays.stream(WINDOWS)
                .forEach((window)->{
                    window.setFill(colour);
                });
    }
}

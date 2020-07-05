package sample.drawing_utils.materials;

import javafx.scene.Node;
import organization.OrganizationWithUId;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class Company {
    public static final int MAX_ROOMS_NUMBER = 8;
    private final OrganizationWithUId LINK;
    private final ArchitectureType TYPE;
    private final Room[] ROOMS;
    private boolean opened = true;

    public Company(OrganizationWithUId link) {
        LINK = link;
        TYPE = ArchitectureType.defineType(LINK.getEmployeesCount());
        ROOMS = new Room[TYPE.ROOMS_NUMBER];
    }

    public Node[] getAllBuildingMaterials() {
        final List<Node> nodes = new ArrayList<>();
        Arrays.stream(ROOMS)
                .forEach((room)->{
                    nodes.addAll(Arrays
                            .asList(room.getAllBuildingMaterials()));
                });
        return nodes.toArray(new Node[0]);
    }

    public void installRoom(int classType, Room room) {
        ROOMS[classType] = room;
    }

    public OrganizationWithUId getLink() {
        return LINK;
    }

    public void close() {
        Arrays.stream(ROOMS)
                .forEach((room)->{
                    room.turnOffLight();
                });
        opened = false;
    }

    public void open() {
        Arrays.stream(ROOMS)
                .forEach((room)->{
                    room.turnOnLight();
                });
        opened = true;
    }
}

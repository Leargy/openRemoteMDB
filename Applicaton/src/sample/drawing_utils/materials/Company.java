package sample.drawing_utils.materials;

import javafx.scene.Node;
import javafx.util.Pair;
import organization.OrganizationWithUId;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public final class Company {
    public static final int MAX_ROOMS_NUMBER = 8;
    private final OrganizationWithUId LINK;
    private final ArchitectureType TYPE;
    private final Room[] ROOMS;
    private boolean opened = true;
    private Random random = new Random();


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

    public void close(int density) {
        Arrays.stream(ROOMS)
                .forEach((room)->{
                    if (random.nextInt() % 100 <= density) {
                        room.turnOffLight();
                    }else room.turnOnLight();
                });
        opened = false;
    }

    public void open(int density) {
        Arrays.stream(ROOMS)
                .forEach((room)->{
                    if (random.nextInt() % 100 <= density ) {
                        room.turnOnLight();
                    }else room.turnOffLight();
                });
        opened = true;
    }

    public boolean isIntersecs(Pair<Double, Double> coordinates) {
//        boolean final isIntersecs = false;
        short[] result = new short[]{0};
        Arrays.stream(ROOMS).forEach((room)-> {
            Arrays.stream(room.getAllBuildingMaterials()).forEach((node) -> {
                if (node.contains(coordinates.getKey(), coordinates.getValue())) result[0] = 1;
            });
//            Arrays.stream(room.getAllBuildingMaterials()).filter((node) ->
//                node.contains(coordinates.getKey(),coordinates.getValue()));
//            result[0] |= room.isIntersects(coordinates);
        });
        return result[0] == 1;
    }
}

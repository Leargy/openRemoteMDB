package sample.drawing_utils.mappers.rooms;

import javafx.scene.Group;
import javafx.util.Pair;
import organization.OrganizationWithUId;

public class HallMapper {
    public static final double DEFAULT_WIDTH = 80;
    public static final double DEFAULT_HEIGHT = 80;
    public static Pair<Double, Double> getLeftUpperCorner(OrganizationWithUId organization, Group group) {
        // TODO: write some logic for out of bounds checking
        return new Pair<Double, Double>(
                Double.valueOf(organization.getCoordinates().getX()),
                Double.valueOf(organization.getCoordinates().getY()));
    }
}

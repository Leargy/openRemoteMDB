package sample.drawing_utils.mappers.rooms;

import javafx.scene.Group;
import javafx.util.Pair;
import organization.OrganizationWithUId;

public class RoofMapper {
    public static final double DEFAULT_WIDTH = 10;
    public static final double DEFAULT_HEIGHT = 10;
    public static Pair<Double, Double> getLeftUpperCorner(OrganizationWithUId organization, Group group) {
        Pair<Double, Double> anchor = AtticMapper.getLeftUpperCorner(organization, group);
        return new Pair<Double, Double>(anchor.getKey() + DEFAULT_WIDTH, anchor.getValue() - DEFAULT_HEIGHT);
    }
}

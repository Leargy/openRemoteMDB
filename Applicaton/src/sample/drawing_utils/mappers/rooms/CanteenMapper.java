package sample.drawing_utils.mappers.rooms;

import javafx.scene.Group;
import javafx.util.Pair;
import organization.OrganizationWithUId;

public class CanteenMapper {
    public static final double DEFAULT_WIDTH = 80;
    public static final double DEFAULT_HEIGHT = 80;
    public static Pair<Double, Double> getLeftUpperCorner(OrganizationWithUId organization, Group group) {
        Pair<Double,Double> point = HallMapper.getLeftUpperCorner(organization, group);
        return new Pair<Double, Double>(point.getKey() + HallMapper.DEFAULT_WIDTH, point.getValue());
    }
}

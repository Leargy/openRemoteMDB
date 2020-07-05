package sample.drawing_utils.mappers.rooms;

import javafx.scene.Group;
import javafx.util.Pair;
import organization.OrganizationWithUId;

public class AtticMapper {
    public static final double DEFAULT_WIDTH = 20;
    public static final double DEFAULT_HEIGHT = 20;
    public static Pair<Double, Double> getLeftUpperCorner(OrganizationWithUId organization, Group group) {
        Pair<Double, Double> anchor = BossMapper.getLeftUpperCorner(organization, group);
        return new Pair<Double, Double>(anchor.getKey(), anchor.getValue() - DEFAULT_HEIGHT);
    }
}

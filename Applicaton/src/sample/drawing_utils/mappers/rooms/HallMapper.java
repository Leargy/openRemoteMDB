package sample.drawing_utils.mappers.rooms;

import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.util.Pair;
import organization.OrganizationWithUId;

public class HallMapper {
    public static final double DEFAULT_WIDTH = 80;
    public static final double DEFAULT_HEIGHT = 80;
    public static Pair<Double, Double> getLeftUpperCorner(OrganizationWithUId organization, Group group) {
        boolean flag = isOutOfBounds(organization, group);
        if (!flag)
            return new Pair<Double, Double>(
                    Double.valueOf(organization.getCoordinates().getX()),
                    Double.valueOf(organization.getCoordinates().getY()));
        else return shiftAnchor(organization, group);
    }

    public static boolean isOutOfBounds(OrganizationWithUId organization, Group group) {
        double x = organization.getCoordinates().getX();
        double y = organization.getCoordinates().getY();
        double minX = x - OfficeMapper.DEFAULT_WIDTH;
        double minY = y - BossMapper.DEFAULT_HEIGHT - AtticMapper.DEFAULT_HEIGHT - RoofMapper.DEFAULT_HEIGHT;
        double maxX = x + DEFAULT_WIDTH + CanteenMapper.DEFAULT_WIDTH;
        double maxY = y + DEFAULT_HEIGHT;
        double beginX = group.getLayoutX();
        double beginY = group.getLayoutY();
        double width = group.getBoundsInParent().getWidth();
        double height = group.getBoundsInParent().getHeight();
        if ((minX + beginX < 0) || (minY + beginY < 0) || (maxX + beginX > width) || (maxY + beginY > height))
            return true;
        else return false;
    }

    private static Pair<Double, Double> shiftAnchor(OrganizationWithUId organizationWithUId, Group group) {
        double x = organizationWithUId.getCoordinates().getX();
        double y = organizationWithUId.getCoordinates().getY();
        double minX = x - OfficeMapper.DEFAULT_WIDTH;
        double minY = y - BossMapper.DEFAULT_HEIGHT - AtticMapper.DEFAULT_HEIGHT - RoofMapper.DEFAULT_HEIGHT;
        double maxX = x + DEFAULT_WIDTH + CanteenMapper.DEFAULT_WIDTH;
        double maxY = y + DEFAULT_HEIGHT;
//        Bounds bounds = group.getLayoutBounds();
//        if (minX < bounds.getMinX())
//            x += bounds.getMinX() - minX;
//        if (maxX > bounds.getMaxX())
//            x += bounds.getMaxX() - maxX;
//        if (minY < bounds.getMinY())
//            y += bounds.getMinY() - minY;
//        if (maxY > bounds.getMaxY())
//            y += bounds.getMaxX() - maxY;
        double beginX = group.getLayoutX();
        double beginY = group.getLayoutY();
        double width = group.getBoundsInParent().getWidth();
        double height = group.getBoundsInParent().getHeight();
        if (minX + beginX < 0)
            x += minX;
        if (minY + beginY < 0)
            y += minY;
        if (maxX + beginX > width)
            x -= maxX;
        if (maxY + beginY > height)
            y -= maxY;
        return new Pair<Double, Double>(x, y);
    }
}

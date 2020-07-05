package sample.drawing_utils.materials;

import javafx.scene.paint.Paint;
import javafx.scene.shape.Polygon;
import organization.OrganizationWithUId;

public final class Flag extends Polygon implements Linkable {
    private OrganizationWithUId LINK;

    public Flag(double x, double y, double width, double height, Paint fill) {
        super(new double[] {
                x, y,
                x, y - height,
                x + width, y - height / 2
        });
        setFill(fill);
    }

    public void setLink(OrganizationWithUId link) {
        LINK = link;
    }

    @Override
    public OrganizationWithUId getLink() {
        return LINK;
    }
}

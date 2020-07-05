package sample.drawing_utils.materials;

import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import organization.OrganizationWithUId;

public final class Window extends Rectangle implements Linkable {
    private OrganizationWithUId LINK;

    public Window(double x, double y, double width, double height, Paint colour) {
        super(x, y, width, height);
        setFill(colour);
    }

    @Override
    public void setLink(OrganizationWithUId link) {
        LINK = link;
    }

    @Override
    public OrganizationWithUId getLink() {
        return LINK;
    }
}

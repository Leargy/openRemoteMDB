package sample.drawing_utils.materials.background;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

public class Bush extends Rectangle {
    public static final Color DAY_COLOUR = Color.FORESTGREEN;
    public static final Color NIGHT_COLOUR = Color.DARKGREEN;
    public static final double DEFAULT_WIDTH = 20;
    public static final double DEFAULT_HEIGHT = 20;

    public Bush(double x, double y, double width, double height, Paint colour) {
        super(x, y, width, height);
        setFill(colour);
    }
}

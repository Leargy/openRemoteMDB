package sample.drawing_utils.materials.background;

import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.Arrays;
import java.util.Random;

public class Field {
    public static final Color SURFACE_DAY = Color.LAWNGREEN;
    public static final Color SURFACE_NIGHT = Color.DARKSLATEGREY;
    protected final Rectangle BACKGROUND;
    public static final int MAX_BUSHES_COUNT = 20;
    private final Bush[] BUSHES;

    public Field(Group resident) {
        Bounds currentBounds = resident.getLayoutBounds();
        double x = currentBounds.getMinX();
        double y = currentBounds.getMinY();
        BACKGROUND = new Rectangle(x, y, currentBounds.getWidth(), currentBounds.getHeight());
        BACKGROUND.setFill(SURFACE_DAY);
        BUSHES = new Bush[MAX_BUSHES_COUNT];
        Arrays.stream(BUSHES).forEach((bush)->{
            bush = createRandomBush(x, y, BACKGROUND.getWidth(), BACKGROUND.getHeight());
        });
    }

    protected Bush createRandomBush(double minx, double miny, double width, double height) {
        Random random = new Random();
        double x = random.nextDouble() * width + minx;
        double y = random.nextDouble() * height + miny;
        return new Bush(x, y, Bush.DEFAULT_WIDTH, Bush.DEFAULT_HEIGHT, Bush.DAY_COLOUR);
    }

    public Node[] getAllComponents() {
        Node[] nodes = new Node[1 + MAX_BUSHES_COUNT];
        nodes[0] = BACKGROUND;
        System.arraycopy(BUSHES, 0, nodes, 1, MAX_BUSHES_COUNT);
        return nodes;
    }

}

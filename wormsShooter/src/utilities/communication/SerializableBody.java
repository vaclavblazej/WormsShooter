package utilities.communication;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.io.Serializable;
import objects.TestBody;
import utilities.ViewInterface;

/**
 *
 * @author Skarab
 */
public class SerializableBody implements Serializable {

    private Point.Double position;
    private Point.Double velocity;
    private Action movement;
    private Dimension REAL_SIZE;
    private boolean jump;

    public SerializableBody(Point2D.Double position, Point2D.Double velocity,
            Action movement, Dimension REAL_SIZE,
            boolean jump) {
        this.position = position;
        this.velocity = velocity;
        this.movement = movement;
        this.REAL_SIZE = REAL_SIZE;
        this.jump = jump;
    }

    public TestBody deserialize(ViewInterface map) {
        return new TestBody(position, velocity, movement, REAL_SIZE, jump, map);
    }
}

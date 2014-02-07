package utilities.communication;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.io.Serializable;
import objects.Body;
import objects.MoveAction;
import utilities.AbstractView;

/**
 *
 * @author Skarab
 */
public class SerializableBody implements Serializable {

    private Point.Double position;
    private Point.Double velocity;
    private MoveAction movement;
    private Dimension REAL_SIZE;
    private boolean jump;

    public SerializableBody(Point2D.Double position, Point2D.Double velocity,
            MoveAction movement, Dimension REAL_SIZE,
            boolean jump) {
        this.position = position;
        this.velocity = velocity;
        this.movement = movement;
        this.REAL_SIZE = REAL_SIZE;
        this.jump = jump;
    }

    public Body deserialize(AbstractView map) {
        return new Body(position, velocity, movement, REAL_SIZE, jump, map);
    }
}

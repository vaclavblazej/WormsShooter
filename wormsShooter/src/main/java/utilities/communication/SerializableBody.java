package utilities.communication;

import objects.Body;
import objects.MoveEnum;
import utilities.AbstractView;

import java.awt.*;
import java.awt.geom.Point2D;
import java.io.Serializable;

/**
 * @author Václav Blažej
 */
public class SerializableBody implements Serializable {

    private Point.Double position;
    private Point.Double velocity;
    private MoveEnum movement;
    private Dimension REAL_SIZE;
    private boolean jump;

    public SerializableBody(Point2D.Double position, Point2D.Double velocity,
                            MoveEnum movement, Dimension REAL_SIZE,
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

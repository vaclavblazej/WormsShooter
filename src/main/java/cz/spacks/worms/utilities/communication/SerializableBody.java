package cz.spacks.worms.utilities.communication;

import cz.spacks.worms.objects.Body;
import cz.spacks.worms.objects.MoveEnum;
import cz.spacks.worms.utilities.AbstractView;

import java.awt.*;
import java.awt.geom.Point2D;

/**
 * @author V�clav Bla�ej
 */
public class SerializableBody implements DeseriazibleInto<Body> {

    private Point position;
    private Point.Double velocity;
    private MoveEnum horizontalMovement;
    private MoveEnum verticalMovement;
    private Dimension REAL_SIZE;
    private boolean jump;

    public SerializableBody(Point position, Point2D.Double velocity,
                            MoveEnum horizontalMovement,
                            MoveEnum verticalMovement,
                            Dimension REAL_SIZE,
                            boolean jump) {
        this.position = position;
        this.velocity = velocity;
        this.horizontalMovement = horizontalMovement;
        this.verticalMovement = verticalMovement;
        this.REAL_SIZE = REAL_SIZE;
        this.jump = jump;
    }

    @Override
    public Body deserialize(AbstractView map) {
        return new Body(position, velocity, horizontalMovement, verticalMovement, REAL_SIZE, jump, map);
    }
}

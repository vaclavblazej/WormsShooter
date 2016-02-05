package cz.spacks.worms.controller.comunication.serialization;

import cz.spacks.worms.model.objects.Body;
import cz.spacks.worms.model.objects.MoveEnum;

import java.awt.*;

/**
 *
 */
public class SerializableBody implements SeriaziblePair<Body, SerializableBody> {

    private Point position;
    private Point.Double velocity;
    private MoveEnum horizontalMovement;
    private MoveEnum verticalMovement;
    private Dimension REAL_SIZE;
    private boolean jump;

    @Override
    public SerializableBody serialize(Body body) {
        this.position = body.getPosition();
        this.velocity = body.getVelocity();
        this.horizontalMovement = body.getHorizontalMovement();
        this.verticalMovement = body.getVerticalMovement();
        this.REAL_SIZE = body.getBodyBlockSize();
        this.jump = body.getJump();
        return this;
    }

    @Override
    public Body deserialize() {
        return new Body(position, velocity, horizontalMovement, verticalMovement, REAL_SIZE, jump);
    }
}

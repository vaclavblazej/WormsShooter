package cz.spacks.worms.controller.comunication.serialization;

import cz.spacks.worms.model.objects.Body;

import java.awt.*;

/**
 *
 */
public class SerializableBody implements SeriaziblePair<Body, SerializableBody> {

    private Point position;
    private Point.Double velocity;
    private Dimension bodyBlockSize;
    private boolean jump;

    @Override
    public SerializableBody serialize(Body body) {
        this.position = body.getPosition();
        this.velocity = body.getVelocity();
        this.bodyBlockSize = body.getBodyBlockSize();
        return this;
    }

    @Override
    public Body deserialize() {
        return new Body(position, velocity, bodyBlockSize);
    }
}

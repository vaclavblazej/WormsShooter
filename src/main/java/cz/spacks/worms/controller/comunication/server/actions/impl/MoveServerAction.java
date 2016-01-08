package cz.spacks.worms.controller.comunication.server.actions.impl;

import cz.spacks.worms.model.objects.Body;
import cz.spacks.worms.model.objects.MoveEnum;
import cz.spacks.worms.controller.comunication.server.actions.ActionServer;

import java.awt.*;

/**
 *
 */
public class MoveServerAction extends ActionServer {

    private int positionX;
    private int positionY;
    private double velocityX;
    private double velocityY;
    private MoveEnum action;

    public MoveServerAction(int id, Point position, Point.Double velocity, MoveEnum action) {
        super(id);
        this.positionX = position.x;
        this.positionY = position.y;
        this.velocityX = velocity.x;
        this.velocityY = velocity.y;
        this.action = action;
    }

    @Override
    public void perform() {
        Body body = view.getModel().getControls().get(id);
        if (body != null) {
            body.setPosition(new Point(positionX, positionY));
            body.setVelocity(new Point.Double(velocityX, velocityY));
            body.control(action);
        }
    }
}

package cz.spacks.worms.controller.comunication.client.actions.impl;

import cz.spacks.worms.controller.comunication.client.actions.ActionClient;
import cz.spacks.worms.controller.comunication.server.actions.impl.ShootServerAction;
import cz.spacks.worms.model.objects.Body;

import java.awt.*;

/**
 *
 */
public class ShootAction extends ActionClient {

    private Point p;

    public ShootAction(Point p) {
        this.p = p;
    }

    @Override
    public void perform() {
        Body body = worldService.getWorldModel().getControls().get(id);
        Point pos = (Point) body.getPosition().clone();
        double rad = Math.atan2(p.y - pos.y, p.x - pos.x);
//        worldService.addObject(new Bullet(pos, rad, worldService)); // todo fix objects
//        serverCommunication.broadcast(new ShootServerAction(pos, rad));
    }
}

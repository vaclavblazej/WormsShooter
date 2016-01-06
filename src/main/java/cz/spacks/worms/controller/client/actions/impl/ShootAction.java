package cz.spacks.worms.controller.client.actions.impl;

import cz.spacks.worms.controller.client.actions.ActionClient;
import cz.spacks.worms.model.objects.Body;
import cz.spacks.worms.model.objects.Bullet;
import cz.spacks.worms.controller.server.ServerCommunication;
import cz.spacks.worms.controller.server.actions.impl.ShootServerAction;

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
        Body body = view.getModel().getControls().get(id);
        Point pos = (Point) body.getPosition().clone();
        double rad = Math.atan2(p.y - pos.y, p.x - pos.x);
        view.addObject(new Bullet(pos, rad, view));
        ServerCommunication.getInstance().broadcast(new ShootServerAction(pos, rad));
    }
}
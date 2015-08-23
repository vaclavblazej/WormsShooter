package client.actions.impl;

import client.actions.ActionClient;
import objects.Body;
import objects.Bullet;
import server.ServerCommunication;
import server.actions.impl.ShootServerAction;

import java.awt.*;

/**
 * @author V�clav Bla�ej
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

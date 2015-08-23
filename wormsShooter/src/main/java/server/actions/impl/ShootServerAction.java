package server.actions.impl;

import objects.Bullet;
import server.actions.ActionServer;

import java.awt.*;

/**
 * @author Václav Blažej
 */
public class ShootServerAction extends ActionServer {

    private Point p;
    private double d;

    public ShootServerAction(Point p, double d) {
        this.p = p;
        this.d = d;
    }

    @Override
    public void perform() {
        view.addObject(new Bullet(p, d, view));
    }
}

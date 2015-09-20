package cz.spacks.worms.server.actions.impl;

import cz.spacks.worms.objects.Bullet;
import cz.spacks.worms.server.actions.ActionServer;

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

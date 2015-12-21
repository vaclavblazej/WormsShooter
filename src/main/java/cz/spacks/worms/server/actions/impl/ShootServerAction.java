package cz.spacks.worms.server.actions.impl;

import cz.spacks.worms.objects.Bullet;
import cz.spacks.worms.server.actions.ActionServer;

import java.awt.*;

/**
 *
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
        System.out.println("new bullet on client side");
        view.addObject(new Bullet(p, d, view));
    }
}

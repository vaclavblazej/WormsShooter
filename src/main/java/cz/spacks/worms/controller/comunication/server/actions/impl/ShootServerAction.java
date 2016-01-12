package cz.spacks.worms.controller.comunication.server.actions.impl;

import cz.spacks.worms.model.objects.Bullet;
import cz.spacks.worms.controller.comunication.server.actions.ActionServer;

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
        System.out.println("new bullet on views side");
        view.addObject(new Bullet(p, d, view));
    }
}

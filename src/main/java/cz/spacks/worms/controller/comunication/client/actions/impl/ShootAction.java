package cz.spacks.worms.controller.comunication.client.actions.impl;

import cz.spacks.worms.controller.comunication.client.actions.ActionClient;

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
    }
}

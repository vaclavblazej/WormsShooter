package cz.spacks.worms.controller.comunication.client.actions.impl;

import cz.spacks.worms.controller.comunication.client.actions.ActionClient;

import java.awt.*;

/**
 *
 */
public class MineAction extends ActionClient {

    private Point point;

    public MineAction(Point point) {
        this.point = point;
    }

    @Override
    public void perform() {
    }
}

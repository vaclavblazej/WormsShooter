package cz.spacks.worms.controller.comunication.server.actions.impl;

import cz.spacks.worms.controller.comunication.server.actions.ActionServer;
import cz.spacks.worms.controller.materials.MaterialEnum;

import java.awt.*;

/**
 *
 */
public class MineServerAction extends ActionServer {
    private Point p;
    private MaterialEnum material;

    public MineServerAction(Point p, MaterialEnum material) {
        this.p = p;
        this.material = material;
    }

    @Override
    public void perform() {
        view.change(p.x, p.y, material);
    }
}

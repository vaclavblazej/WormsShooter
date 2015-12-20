package cz.spacks.worms.server.actions.impl;

import cz.spacks.worms.server.actions.ActionServer;
import cz.spacks.worms.utilities.materials.MaterialEnum;

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
        view.getModel().getMap().recalculateShadows(p);
    }
}

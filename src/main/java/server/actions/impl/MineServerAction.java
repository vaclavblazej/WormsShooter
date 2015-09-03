package server.actions.impl;

import server.actions.ActionServer;
import utilities.materials.MaterialEnum;

import java.awt.*;

/**
 * @author Václav Blažej
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

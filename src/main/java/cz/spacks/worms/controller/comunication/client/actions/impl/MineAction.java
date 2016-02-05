package cz.spacks.worms.controller.comunication.client.actions.impl;

import cz.spacks.worms.controller.Settings;
import cz.spacks.worms.controller.comunication.client.actions.ActionClient;
import cz.spacks.worms.controller.comunication.server.actions.impl.MineServerAction;
import cz.spacks.worms.controller.comunication.server.actions.impl.ObtainServerAction;
import cz.spacks.worms.controller.materials.MaterialEnum;
import cz.spacks.worms.model.objects.Body;

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
        int x = point.x / Settings.BLOCK_SIZE;
        int y = point.y / Settings.BLOCK_SIZE;
        Body body = worldService.getWorldModel().getControls().get(id);
        Point pos = body.getPosition();
        int distance = Math.abs(pos.x / Settings.BLOCK_SIZE - x)
                + Math.abs(pos.y / Settings.BLOCK_SIZE - y);
        if (distance < 6) {
            MaterialEnum to = MaterialEnum.AIR;
            MaterialEnum mat = worldService.change(x, y, to);
            body.getInventory().addAll(worldService.getMaterialModel().getComponents(mat));
//            serverCommunication.broadcast(new ObtainServerAction(id, mat));
//            serverCommunication.broadcast(new MineServerAction(new Point(x, y), to));
        }
    }
}

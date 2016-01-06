package cz.spacks.worms.controller.client.actions.impl;

import cz.spacks.worms.controller.Settings;
import cz.spacks.worms.controller.client.actions.ActionClient;
import cz.spacks.worms.model.objects.Body;
import cz.spacks.worms.controller.server.ServerCommunication;
import cz.spacks.worms.controller.server.actions.impl.MineServerAction;
import cz.spacks.worms.controller.server.actions.impl.ObtainServerAction;
import cz.spacks.worms.controller.materials.MaterialEnum;

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
        Body body = view.getModel().getControls().get(id);
        Point pos = body.getPosition();
        int distance = Math.abs(pos.x / Settings.BLOCK_SIZE - x)
                + Math.abs(pos.y / Settings.BLOCK_SIZE - y);
        if (distance < 6) {
            MaterialEnum to = MaterialEnum.AIR;
            MaterialEnum mat = view.change(x, y, to);
            body.getInventory().add(view.getMaterialModel().getComponents(mat));
            ServerCommunication service = ServerCommunication.getInstance();
            service.broadcast(new ObtainServerAction(id, mat));
            service.broadcast(new MineServerAction(new Point(x, y), to));
        }
    }
}

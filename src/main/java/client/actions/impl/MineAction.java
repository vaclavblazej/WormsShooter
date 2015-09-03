package client.actions.impl;

import client.actions.ActionClient;
import main.Application;
import objects.Body;
import server.ServerCommunication;
import server.actions.impl.MineServerAction;
import server.actions.impl.ObtainServerAction;
import utilities.materials.MaterialEnum;

import java.awt.*;

/**
 * @author Václav Blažej
 */
public class MineAction extends ActionClient {

    private Point point;

    public MineAction(Point point) {
        this.point = point;
    }

    @Override
    public void perform() {
        int x = point.x / Application.BLOCK_SIZE;
        int y = point.y / Application.BLOCK_SIZE;
        Body body = view.getModel().getControls().get(id);
        Point pos = body.getPosition();
        int distance = Math.abs(pos.x / Application.BLOCK_SIZE - x)
                + Math.abs(pos.y / Application.BLOCK_SIZE - y);
        if (distance < 6) {
            MaterialEnum to = MaterialEnum.AIR;
            MaterialEnum mat = view.change(x, y, to);
            body.getInventory().add(view.getMaterial().getComponents(mat));
            ServerCommunication service = ServerCommunication.getInstance();
            service.broadcast(new ObtainServerAction(id, mat));
            service.broadcast(new MineServerAction(new Point(x, y), to));
        }
    }
}

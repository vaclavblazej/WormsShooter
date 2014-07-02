package client.actions.impl;

import client.actions.ActionClient;
import server.actions.impl.MineServerAction;
import server.actions.impl.ObtainServerAction;
import java.awt.Point;
import main.Main;
import objects.Body;
import server.ServerCommunication;
import utilities.materials.MaterialEnum;

/**
 *
 * @author Skarab
 */
public class MineAction extends ActionClient {

    private Point point;

    public MineAction(Point point) {
        this.point = point;
    }

    @Override
    public void perform() {
        int x = (int) (point.x / Main.RATIO);
        int y = (int) (point.y / Main.RATIO);
        Body body = view.getModel().getControls().get(id);
        Point pos = body.getPosition();
        int distance = Math.abs((int) (pos.x / Main.RATIO) - x)
                + Math.abs((int) (pos.y / Main.RATIO) - y);
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

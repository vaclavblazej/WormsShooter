package dynamic.communication;

import java.awt.Point;
import java.io.IOException;
import java.io.ObjectOutputStream;
import main.Main;
import objects.Body;
import server.ServerComService;
import utilities.AbstractView;
import utilities.communication.Action;
import utilities.communication.Packet;
import utilities.communication.PacketBuilder;
import utilities.materials.MaterialEnum;

/**
 *
 * @author Skarab
 */
public class mine extends Packet {

    @Override
    public void performClient(ObjectOutputStream os, Packet packet, AbstractView view) throws IOException {
        Point p = (Point) packet.get(0);
        view.change(p.x, p.y, (MaterialEnum) packet.get(1));
        view.getModel().getMap().recalculateShadows(p);
    }

    @Override
    public void performServer(ObjectOutputStream os, Packet packet, AbstractView view) throws IOException {
        Point p = (Point) packet.get(0);
        int id = packet.getId();
        int x = p.x / Main.RATIO;
        int y = p.y / Main.RATIO;
        ServerComService service = ServerComService.getInstance();
        Body body = view.getModel().getControls().get(id);
        Point pos = body.getPosition();
        int distance = Math.abs(pos.x - x) + Math.abs(pos.y - y);
        if (distance < 6) {
            MaterialEnum to = MaterialEnum.AIR;
            MaterialEnum mat = view.change(x, y, to);
            body.getInventory().add(view.getMaterial().getComponents(mat));
            service.broadcast(new PacketBuilder(Action.OBTAIN, id).addInfo(mat));
            service.broadcast(new PacketBuilder(Action.MINE, id)
                    .addInfo(new Point(x, y)).addInfo(to));
        }
    }
}

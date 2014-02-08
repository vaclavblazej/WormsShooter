package dynamic.communication;

import java.awt.Point;
import java.io.IOException;
import java.io.ObjectOutputStream;
import main.Main;
import objects.Body;
import objects.Bullet;
import server.ServerComService;
import utilities.AbstractView;
import utilities.communication.Action;
import utilities.communication.Packet;
import utilities.communication.PacketBuilder;

/**
 *
 * @author Skarab
 */
public class shoot extends Packet {

    @Override
    public void performClient(ObjectOutputStream os, Packet packet, AbstractView view) throws IOException {
        Point p = (Point) packet.get(0);
        double d = (Double) packet.get(1);
        view.addObject(new Bullet(p, d, view));
    }

    @Override
    public void performServer(ObjectOutputStream os, Packet packet, AbstractView view) throws IOException {
        int id = packet.getId();
        Point p = (Point) packet.get(0);
        Body body = view.getModel().getControls().get(id);
        Point pos = (Point) body.getPosition().clone();
        pos.x *= Main.RATIO;
        pos.y *= Main.RATIO;
        double rad = Math.atan2(p.y - pos.y, p.x - pos.x);
        view.addObject(new Bullet(pos, rad, view));
        ServerComService.getInstance().broadcast(
                new PacketBuilder(Action.SHOOT).addInfo(pos).addInfo(rad));
    }
}

package dynamic.communication;

import java.awt.Point;
import java.io.IOException;
import java.io.ObjectOutputStream;
import objects.Body;
import objects.MoveAction;
import server.ServerComService;
import utilities.AbstractView;
import utilities.communication.Packet;
import utilities.communication.PacketBuilder;

/**
 *
 * @author Skarab
 */
public class move extends Packet {

    @Override
    public void performServer(ObjectOutputStream os, Packet packet, AbstractView view) throws IOException {
        Body body = view.getModel().getControls().get(packet.getId());
        if (body != null) {
            Point pos = body.getPosition();
            Point.Double vel = body.getVelocity();
            ServerComService.getInstance().broadcast(
                    new PacketBuilder(packet.getAction(), packet.getId())
                    .addInfo(pos)
                    .addInfo(vel.x)
                    .addInfo(vel.y));
            System.out.println((MoveAction) packet.get(0));
            body.control((MoveAction) packet.get(0));
        }
    }
}

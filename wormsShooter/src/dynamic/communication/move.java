package dynamic.communication;

import java.awt.Point;
import java.io.IOException;
import java.io.ObjectOutputStream;
import objects.Body;
import objects.MoveAction;
import server.ServerComService;
import utilities.AbstractView;
import utilities.communication.Action;
import utilities.communication.Packet;
import utilities.communication.PacketBuilder;

/**
 *
 * @author Skarab
 */
public class move extends Packet {

    @Override
    public void performClient(ObjectOutputStream os, Packet packet, AbstractView view) throws IOException {
        Body body = view.getModel().getControls().get(packet.getId());
        if (body != null) {
            body.setPosition((Point) packet.get(1));
//            Exception in thread "Thread-6" java.lang.IndexOutOfBoundsException: Index: 1, Size: 1
//	at java.util.ArrayList.rangeCheck(ArrayList.java:604)
//	at java.util.ArrayList.get(ArrayList.java:382)
//	at utilities.communication.Packet.get(Packet.java:58)
//	at dynamic.communication.move.performClient(move.java:23)
//	at client.ClientCommunication$1.run(ClientCommunication.java:158)
//	at java.lang.Thread.run(Thread.java:724)
            body.setVelocity(new Point.Double((Double) packet.get(2), (Double) packet.get(3)));
            body.control((MoveAction) packet.get(0));
        }
    }

    @Override
    public void performServer(ObjectOutputStream os, Packet packet, AbstractView view) throws IOException {
        Body body = view.getModel().getControls().get(packet.getId());
        if (body != null) {
            Point pos = body.getPosition();
            Point.Double vel = body.getVelocity();
            ServerComService.getInstance().broadcast(
                    new PacketBuilder(Action.MOVE, packet.getId())
                    .addInfo(packet.get(0))
                    .addInfo(pos)
                    .addInfo(vel.x)
                    .addInfo(vel.y));
            body.control((MoveAction) packet.get(0));
        }
    }
}

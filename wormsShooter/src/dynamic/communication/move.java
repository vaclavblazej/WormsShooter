package dynamic.communication;

import java.awt.Point;
import java.net.Socket;
import server.Performable;
import utilities.communication.Packet;
import utilities.communication.PacketBuilder;

/**
 *
 * @author Skarab
 */
public class move implements Performable {

    @Override
    public void perform(Socket socket, Packet packet) {
        /*
         body = controls.get(id);
         if (body != null) {
         pos = body.getPosition();
         Point.Double vel = body.getVelocity();
         service.broadcast(new PacketBuilder(action, id).addInfo(pos)
         .addInfo(vel.x).addInfo(vel.y));
         body.control(action);
         }*/
    }
}

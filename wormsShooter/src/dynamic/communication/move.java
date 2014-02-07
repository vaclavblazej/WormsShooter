package dynamic.communication;

import java.io.IOException;
import java.io.ObjectOutputStream;
import utilities.communication.Model;
import utilities.communication.Packet;

/**
 *
 * @author Skarab
 */
public class move extends Packet {

    @Override
    public void perform(ObjectOutputStream os, Packet packet, Model model) throws IOException {
        super.perform(os, packet, model);
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

package dynamic.communication;

import java.io.IOException;
import java.io.ObjectOutputStream;
import utilities.communication.Model;
import utilities.communication.Packet;

/**
 *
 * @author Skarab
 */
public class shoot extends Packet {

    @Override
    public void perform(ObjectOutputStream os, Packet packet, Model model) throws IOException {
        super.perform(os, packet, model);
        /*p = (Point) packet.get(0);
         body = controls.get(id);
         pos = (Point) body.getPosition().clone();
         pos.x *= Main.RATIO;
         pos.y *= Main.RATIO;
         double rad = Math.atan2(p.y - pos.y, p.x - pos.x);
         ServerView.getInstance().addObject(new Bullet(pos, rad, ServerView.getInstance()));
         ServerComService.getInstance().broadcast(
         new PacketBuilder(Action.SHOOT).addInfo(pos).addInfo(rad));*/
    }
}

package dynamic.communication;

import java.io.IOException;
import java.io.ObjectOutputStream;
import utilities.communication.Model;
import utilities.communication.Packet;

/**
 *
 * @author Skarab
 */
public class mine extends Packet {

    @Override
    public void perform(ObjectOutputStream os, Packet packet, Model model) throws IOException {
        super.perform(os, packet, model);
        /*p = (Point) packet.get(0);
         int x = p.x / Main.RATIO;
         int y = p.y / Main.RATIO;
         body = controls.get(id);
         pos = body.getPosition();
         int distance = Math.abs(pos.x - x) + Math.abs(pos.y - y);
         if (distance < 6) {
         MaterialEnum to = MaterialEnum.AIR;
         MaterialEnum mat = ServerView.getInstance().change(x, y, to);
         body.getInventory().add(ServerView.getInstance().getMaterial().getComponents(mat));
         service.broadcast(new PacketBuilder(Action.OBTAIN, id).addInfo(mat));
         service.broadcast(new PacketBuilder(Action.MINE, id)
         .addInfo(new Point(x, y)).addInfo(to));
         }*/
    }
}

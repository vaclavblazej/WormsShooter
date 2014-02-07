package dynamic.communication;

import java.awt.Point;
import java.net.Socket;
import main.Main;
import objects.Bullet;
import server.Performable;
import server.ServerComService;
import server.ServerView;
import utilities.communication.Action;
import utilities.communication.Packet;
import utilities.communication.PacketBuilder;

/**
 *
 * @author Skarab
 */
public class shoot implements Performable {

    @Override
    public void perform(Socket socket, Packet packet) {
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

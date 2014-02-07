package dynamic.communication;

import client.ClientView;
import java.awt.Point;
import java.net.Socket;
import main.Main;
import server.Performable;
import server.ServerView;
import utilities.communication.Action;
import utilities.communication.Packet;
import utilities.communication.PacketBuilder;
import utilities.materials.MaterialEnum;

/**
 *
 * @author Skarab
 */
public class mine implements Performable {

    @Override
    public void perform(Socket socket, Packet packet) {
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

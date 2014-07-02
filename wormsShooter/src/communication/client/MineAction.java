package communication.client;

import communication.server.MineServerAction;
import communication.server.ObtainServerAction;
import java.awt.Point;
import main.Main;
import objects.Body;
import server.ServerComService;
import utilities.AbstractView;
import utilities.communication.PerformablePacket;
import utilities.materials.MaterialEnum;

/**
 *
 * @author Skarab
 */
public class MineAction extends PerformablePacket {

    private Point point;

    public MineAction(Point point) {
        this.point = point;
    }

    @Override
    public void perform(AbstractView view) {
        int x = (int) (point.x / Main.RATIO);
        int y = (int) (point.y / Main.RATIO);
        Body body = view.getModel().getControls().get(id);
        Point pos = body.getPosition();
        int distance = Math.abs((int) (pos.x / Main.RATIO) - x)
                + Math.abs((int) (pos.y / Main.RATIO) - y);
        if (distance < 6) {
            MaterialEnum to = MaterialEnum.AIR;
            MaterialEnum mat = view.change(x, y, to);
            body.getInventory().add(view.getMaterial().getComponents(mat));
            ServerComService service = ServerComService.getInstance();
            service.broadcast(new ObtainServerAction(id, mat));
            service.broadcast(new MineServerAction(new Point(x, y), to));
        }
    }
}
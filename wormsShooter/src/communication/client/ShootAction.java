package communication.client;

import communication.server.ShootServerAction;
import java.awt.Point;
import main.Main;
import objects.Body;
import objects.Bullet;
import server.ServerComService;
import utilities.AbstractView;
import utilities.communication.PerformablePacket;

/**
 *
 * @author Skarab
 */
public class ShootAction extends PerformablePacket {

    private Point p;

    public ShootAction(Point p) {
        this.p = p;
    }

    @Override
    public void perform(AbstractView view) {
        Body body = view.getModel().getControls().get(id);
        Point pos = (Point) body.getPosition().clone();
        double rad = Math.atan2(p.y - pos.y, p.x - pos.x);
        view.addObject(new Bullet(pos, rad, view));
        ServerComService.getInstance().broadcast(new ShootServerAction(pos, rad));
    }
}

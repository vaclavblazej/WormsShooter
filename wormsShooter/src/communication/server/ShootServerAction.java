package communication.server;

import java.awt.Point;
import objects.Bullet;
import utilities.AbstractView;
import utilities.communication.PerformablePacket;

/**
 *
 * @author Skarab
 */
public class ShootServerAction extends PerformablePacket {

    private Point p;
    private double d;

    public ShootServerAction(Point p, double d) {
        this.p = p;
        this.d = d;
    }

    @Override
    public void perform(AbstractView view) {
        view.addObject(new Bullet(p, d, view));
    }
}

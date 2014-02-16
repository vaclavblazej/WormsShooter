package communication.server;

import java.awt.Point;
import utilities.AbstractView;
import utilities.communication.PerformablePacket;
import utilities.materials.MaterialEnum;

/**
 *
 * @author Skarab
 */
public class MineServerAction extends PerformablePacket {
    private Point p;
    private MaterialEnum material;

    public MineServerAction(Point p, MaterialEnum material) {
        this.p = p;
        this.material = material;
    }

    @Override
    public void perform(AbstractView view) {
        view.change(p.x, p.y, material);
        view.getModel().getMap().recalculateShadows(p);
    }
}

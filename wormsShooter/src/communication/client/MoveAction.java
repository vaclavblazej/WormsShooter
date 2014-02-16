package communication.client;

import communication.server.MoveServerAction;
import java.awt.Point;
import objects.Body;
import objects.MoveEnum;
import server.ServerComService;
import utilities.AbstractView;
import utilities.communication.PerformablePacket;

/**
 *
 * @author Skarab
 */
public class MoveAction extends PerformablePacket {

    private MoveEnum action;

    public MoveAction(MoveEnum action) {
        this.action = action;
    }

    @Override
    public void perform(AbstractView view) {
        Body body = view.getModel().getControls().get(id);
        if (body != null) {
            Point pos = body.getPosition();
            Point.Double vel = body.getVelocity();
            ServerComService.getInstance().broadcast(new MoveServerAction(id, pos, vel, action));
            body.control(action);
        }
    }
}

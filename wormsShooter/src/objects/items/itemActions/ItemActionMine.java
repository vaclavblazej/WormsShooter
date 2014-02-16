package objects.items.itemActions;

import client.ClientCommunication;
import communication.client.MineAction;
import java.awt.Point;

/**
 *
 * @author Skarab
 */
public class ItemActionMine implements ItemAction {

    @Override
    public void action(Point point) {
        ClientCommunication.getInstance().send(new MineAction(point));
    }
}

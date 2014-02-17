package objects.items.itemActions;

import client.ClientCommunication;
import communication.client.ShootAction;
import java.awt.Point;

/**
 *
 * @author Skarab
 */
public class ItemActionShoot implements ItemAction {

    @Override
    public void action(Point point) {
        ClientCommunication.getInstance().send(new ShootAction(point));
    }
}

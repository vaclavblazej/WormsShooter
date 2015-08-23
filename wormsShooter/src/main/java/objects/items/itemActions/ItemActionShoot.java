package objects.items.itemActions;

import client.ClientCommunication;
import client.actions.impl.ShootAction;

import java.awt.*;

/**
 * @author Skarab
 */
public class ItemActionShoot implements ItemAction {

    @Override
    public void action(Point point) {
        ClientCommunication.getInstance().send(new ShootAction(point));
    }
}

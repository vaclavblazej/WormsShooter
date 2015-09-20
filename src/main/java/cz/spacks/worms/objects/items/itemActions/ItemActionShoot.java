package cz.spacks.worms.objects.items.itemActions;

import cz.spacks.worms.client.ClientCommunication;
import cz.spacks.worms.client.actions.impl.ShootAction;

import java.awt.*;

/**
 * @author Václav Blažej
 */
public class ItemActionShoot implements ItemAction {

    @Override
    public void action(Point point) {
        ClientCommunication.getInstance().send(new ShootAction(point));
    }
}

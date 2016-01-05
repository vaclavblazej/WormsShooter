package cz.spacks.worms.model.objects.items.itemActions;

import cz.spacks.worms.view.client.ClientCommunication;
import cz.spacks.worms.view.client.actions.impl.ShootAction;

import java.awt.*;

/**
 *
 */
public class ItemActionShoot implements ItemAction {

    @Override
    public void action(Point point) {
        ClientCommunication.getInstance().send(new ShootAction(point));
    }
}

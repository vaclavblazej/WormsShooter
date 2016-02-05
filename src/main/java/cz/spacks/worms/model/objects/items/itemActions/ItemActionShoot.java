package cz.spacks.worms.model.objects.items.itemActions;

import cz.spacks.worms.controller.comunication.client.actions.ActionClient;
import cz.spacks.worms.controller.comunication.client.actions.impl.ShootAction;

import java.awt.*;

/**
 *
 */
public class ItemActionShoot implements ItemAction {

    @Override
    public ActionClient action(Point point) {
        return new ShootAction(point);
    }
}

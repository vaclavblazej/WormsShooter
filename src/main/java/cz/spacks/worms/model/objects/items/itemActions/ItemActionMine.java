package cz.spacks.worms.model.objects.items.itemActions;

import cz.spacks.worms.view.client.ClientCommunication;
import cz.spacks.worms.view.client.actions.impl.MineAction;

import java.awt.*;

/**
 *
 */
public class ItemActionMine implements ItemAction {

    @Override
    public void action(Point point) {
        ClientCommunication.getInstance().send(new MineAction(point));
    }
}

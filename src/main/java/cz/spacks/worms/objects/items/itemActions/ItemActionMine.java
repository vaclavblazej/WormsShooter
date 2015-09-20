package cz.spacks.worms.objects.items.itemActions;

import cz.spacks.worms.client.ClientCommunication;
import cz.spacks.worms.client.actions.impl.MineAction;

import java.awt.*;

/**
 * @author Václav Blažej
 */
public class ItemActionMine implements ItemAction {

    @Override
    public void action(Point point) {
        ClientCommunication.getInstance().send(new MineAction(point));
    }
}

package objects.items.itemActions;

import client.ClientCommunication;
import client.actions.impl.MineAction;

import java.awt.*;

/**
 * @author V�clav Bla�ej
 */
public class ItemActionMine implements ItemAction {

    @Override
    public void action(Point point) {
        ClientCommunication.getInstance().send(new MineAction(point));
    }
}

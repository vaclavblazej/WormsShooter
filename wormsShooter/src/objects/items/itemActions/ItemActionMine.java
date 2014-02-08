package objects.items.itemActions;

import client.ClientCommunication;
import java.awt.Point;
import utilities.communication.Action;
import utilities.communication.PacketBuilder;

/**
 *
 * @author Skarab
 */
public class ItemActionMine implements ItemAction {

    @Override
    public void action(Point point) {
        ClientCommunication.getInstance().send(
                new PacketBuilder(Action.MINE).addInfo(point));
    }
}

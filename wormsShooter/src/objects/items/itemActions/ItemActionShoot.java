package objects.items.itemActions;

import client.ClientCommunication;
import java.awt.Point;
import utilities.communication.Action;
import utilities.communication.PacketBuilder;

/**
 *
 * @author Skarab
 */
public class ItemActionShoot implements ItemAction {

    @Override
    public void action(Point point) {
        ClientCommunication.getInstance().send(
                new PacketBuilder(Action.SHOOT).addInfo(point));
    }
}

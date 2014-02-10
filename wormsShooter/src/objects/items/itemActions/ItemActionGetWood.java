package objects.items.itemActions;

import client.ClientCommunication;
import java.awt.Point;
import utilities.communication.Action;
import utilities.communication.PacketBuilder;
import utilities.materials.MaterialEnum;

/**
 *
 * @author Skarab
 */
public class ItemActionGetWood implements ItemAction {

    @Override
    public void action(Point point) {
        ClientCommunication.getInstance().send(
                new PacketBuilder(Action.OBTAIN).addInfo(MaterialEnum.WOOD));
    }
}

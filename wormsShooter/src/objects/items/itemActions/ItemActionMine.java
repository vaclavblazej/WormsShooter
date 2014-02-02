package objects.items.itemActions;

import client.ClientCommunication;
import java.awt.Point;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;
import utilities.communication.Action;
import utilities.communication.PacketBuilder;

/**
 *
 * @author Skarab
 */
public class ItemActionMine implements ItemAction {

    @Override
    public void action(Point point) {
        try {
            ClientCommunication.getInstance().sendAction(
                    new PacketBuilder(Action.MINE).addInfo(point));
        } catch (RemoteException ex) {
            Logger.getLogger(ItemActionMine.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

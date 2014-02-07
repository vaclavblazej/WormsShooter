package dynamic.communication;

import java.net.Socket;
import objects.items.ComponentTableModel;
import objects.items.Recipe;
import server.Performable;
import server.ServerView;
import utilities.communication.Action;
import utilities.communication.Packet;
import utilities.communication.PacketBuilder;

/**
 *
 * @author Skarab
 */
public class craft implements Performable {

    @Override
    public void perform(Socket socket, Packet packet) {
        /*int idx = (Integer) packet.get(0);
         ComponentTableModel inventory = controls.get(id).getInventory();
         Recipe receipe = ServerView.getInstance().getModel().getFactory()
         .getRecipes().getReceipe(idx);
         inventory.remove(receipe.getIngredients());
         inventory.add(receipe.getProducts());
         service.broadcast(new PacketBuilder(Action.CRAFT, id).addInfo(idx));*/
    }
}

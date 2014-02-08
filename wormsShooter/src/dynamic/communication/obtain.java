package dynamic.communication;

import client.menu.GameWindowItemBar;
import java.io.IOException;
import java.io.ObjectOutputStream;
import objects.items.InventoryTableModel;
import utilities.AbstractView;
import utilities.communication.Packet;
import utilities.materials.MaterialEnum;

/**
 *
 * @author Skarab
 */
public class obtain extends Packet {

    @Override
    public void performClient(ObjectOutputStream os, Packet packet, AbstractView view) throws IOException {
        InventoryTableModel inv = view.getModel().getControls().get(packet.getId()).getInventory();
        MaterialEnum en = (MaterialEnum) packet.get(0);
        inv.add(view.getMaterial().getComponents(en));
        GameWindowItemBar.getInstance().refreshBar(inv);
    }
}

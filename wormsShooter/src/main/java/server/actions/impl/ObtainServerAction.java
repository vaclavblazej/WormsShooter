package server.actions.impl;

import client.ClientCommunication;
import client.menu.GameWindowItemBar;
import objects.items.InventoryTableModel;
import server.actions.ActionServer;
import utilities.materials.MaterialEnum;

/**
 * @author Skarab
 */
public class ObtainServerAction extends ActionServer {

    private MaterialEnum en;

    public ObtainServerAction(int id, MaterialEnum en) {
        super(id);
        this.en = en;
    }

    @Override
    public void perform() {
        InventoryTableModel inv = view.getModel().getControls().get(id).getInventory();
        inv.add(view.getMaterial().getComponents(en));
        if (ClientCommunication.getInstance().getInfo().getId() == id) {
            GameWindowItemBar.getInstance().refreshBar(inv);
        }
    }
}

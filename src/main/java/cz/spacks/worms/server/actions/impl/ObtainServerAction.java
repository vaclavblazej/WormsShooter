package cz.spacks.worms.server.actions.impl;

import cz.spacks.worms.client.ClientCommunication;
import cz.spacks.worms.client.menu.GameWindowItemBar;
import cz.spacks.worms.objects.items.InventoryTableModel;
import cz.spacks.worms.server.actions.ActionServer;
import cz.spacks.worms.utilities.materials.MaterialEnum;

/**
 * @author Václav Blažej
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

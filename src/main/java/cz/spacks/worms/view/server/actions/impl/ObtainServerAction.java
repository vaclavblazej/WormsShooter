package cz.spacks.worms.view.server.actions.impl;

import cz.spacks.worms.view.client.ClientCommunication;
import cz.spacks.worms.view.client.menu.GameWindowItemBar;
import cz.spacks.worms.model.objects.items.InventoryTableModel;
import cz.spacks.worms.view.server.actions.ActionServer;
import cz.spacks.worms.controller.materials.MaterialEnum;

/**
 *
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

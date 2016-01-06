package cz.spacks.worms.controller.server.actions.impl;

import cz.spacks.worms.controller.client.ClientCommunication;
import cz.spacks.worms.model.objects.Inventory;
import cz.spacks.worms.view.client.menu.GameWindowItemBar;
import cz.spacks.worms.view.client.menu.InventoryViewModel;
import cz.spacks.worms.controller.server.actions.ActionServer;
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
        Inventory inv = view.getModel().getControls().get(id).getInventory();
        inv.add(view.getMaterialModel().getComponents(en));
        // todo automatic refresh changed inventory view
    }
}

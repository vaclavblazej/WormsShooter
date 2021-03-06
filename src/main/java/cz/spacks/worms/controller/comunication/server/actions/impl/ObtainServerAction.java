package cz.spacks.worms.controller.comunication.server.actions.impl;

import cz.spacks.worms.controller.comunication.server.actions.ActionServer;
import cz.spacks.worms.controller.materials.MaterialEnum;
import cz.spacks.worms.model.objects.Inventory;

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
        Inventory inv = worldService.getWorldModel().getControls().get(id).getInventory();
        inv.addAll(worldService.getMaterialModel().getComponents(en));
        // todo automatic refresh changed inventory worldService
    }
}

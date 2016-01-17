package cz.spacks.worms.controller.comunication.server.actions.impl;

import cz.spacks.worms.controller.comunication.server.actions.ActionServer;
import cz.spacks.worms.model.objects.Body;

/**
 *
 */
public class NewPlayerServerAction extends ActionServer {

    public NewPlayerServerAction(int id) {
        super(id);
    }

    public void perform() {
        Body b = worldService.newBody();
        worldService.getWorldModel().getControls().put(getId(), b);
    }
}

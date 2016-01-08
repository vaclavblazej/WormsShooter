package cz.spacks.worms.controller.comunication.server.actions.impl;

import cz.spacks.worms.view.client.ClientView;
import cz.spacks.worms.model.objects.Body;
import cz.spacks.worms.controller.comunication.server.actions.ActionServer;

/**
 *
 */
public class NewPlayerServerAction extends ActionServer {

    public NewPlayerServerAction(int id) {
        super(id);
    }

    public void perform() {
        Body b = ClientView.getInstance().newBody();
        view.getModel().getControls().put(getId(), b);
    }
}

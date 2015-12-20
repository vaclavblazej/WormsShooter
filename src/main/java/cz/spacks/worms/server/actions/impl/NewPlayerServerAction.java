package cz.spacks.worms.server.actions.impl;

import cz.spacks.worms.client.ClientView;
import cz.spacks.worms.objects.Body;
import cz.spacks.worms.server.actions.ActionServer;

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

package cz.spacks.worms.controller.server.actions.impl;

import cz.spacks.worms.controller.client.ClientCommunication;
import cz.spacks.worms.view.client.ClientView;
import cz.spacks.worms.controller.server.actions.ActionServer;

/**
 *
 */
public class DisconnectServerAction extends ActionServer {

    public DisconnectServerAction(int id) {
        super(id);
    }

    @Override
    public void perform() {
        if (id == ClientCommunication.getInstance().getInfo().getId()) {
            ClientView.getInstance().reset();
        } else {
            ClientCommunication.getInstance().unbindBody(id);
        }
    }
}

package cz.spacks.worms.view.server.actions.impl;

import cz.spacks.worms.view.client.ClientCommunication;
import cz.spacks.worms.view.client.ClientView;
import cz.spacks.worms.view.server.actions.ActionServer;

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

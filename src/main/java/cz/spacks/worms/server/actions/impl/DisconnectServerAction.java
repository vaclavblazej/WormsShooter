package cz.spacks.worms.server.actions.impl;

import cz.spacks.worms.client.ClientCommunication;
import cz.spacks.worms.client.ClientView;
import cz.spacks.worms.server.actions.ActionServer;

/**
 * @author Václav Blažej
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

package cz.spacks.worms.controller.client.actions.impl;

import cz.spacks.worms.controller.client.actions.ActionClient;
import cz.spacks.worms.controller.server.ServerCommunication;
import cz.spacks.worms.controller.server.actions.impl.DisconnectServerAction;

/**
 *
 */
public class DisconnectAction extends ActionClient {

    @Override
    public void perform() {
        // todo broadcast?
        ServerCommunication.getInstance().send(id, new DisconnectServerAction(id));
        ServerCommunication.getInstance().disconnect(id);
    }
}

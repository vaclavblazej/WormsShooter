package cz.spacks.worms.controller.comunication.client.actions.impl;

import cz.spacks.worms.controller.comunication.client.actions.ActionClient;
import cz.spacks.worms.controller.comunication.server.ServerCommunication;
import cz.spacks.worms.controller.comunication.server.actions.impl.DisconnectServerAction;

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

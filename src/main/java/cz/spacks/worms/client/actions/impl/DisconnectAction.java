package cz.spacks.worms.client.actions.impl;

import cz.spacks.worms.client.actions.ActionClient;
import cz.spacks.worms.server.ServerCommunication;
import cz.spacks.worms.server.actions.impl.DisconnectServerAction;

/**
 * @author Václav Blažej
 */
public class DisconnectAction extends ActionClient {

    @Override
    public void perform() {
        // todo broadcast?
        ServerCommunication.getInstance().send(id, new DisconnectServerAction(id));
        ServerCommunication.getInstance().disconnect(id);
    }
}

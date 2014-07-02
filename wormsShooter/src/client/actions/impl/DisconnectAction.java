package client.actions.impl;

import client.actions.ActionClient;
import server.ServerCommunication;
import server.actions.impl.DisconnectServerAction;

/**
 *
 * @author Skarab
 */
public class DisconnectAction extends ActionClient {

    @Override
    public void perform() {
        ServerCommunication.getInstance().send(id, new DisconnectServerAction(id));
        ServerCommunication.getInstance().disconnect(id);
    }
}

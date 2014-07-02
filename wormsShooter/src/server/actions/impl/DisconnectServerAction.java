package server.actions.impl;

import client.ClientCommunication;
import client.ClientView;
import server.actions.ActionServer;
import utilities.AbstractView;

/**
 *
 * @author Skarab
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

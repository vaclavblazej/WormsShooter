package cz.spacks.worms.controller.comunication.server.actions.impl;

import cz.spacks.worms.controller.comunication.client.ClientCommunication;
import cz.spacks.worms.view.views.ClientView;
import cz.spacks.worms.controller.comunication.server.actions.ActionServer;

/**
 *
 */
public class DisconnectServerAction extends ActionServer {

    public DisconnectServerAction(int id) {
        super(id);
    }

    @Override
    public void perform() {
        if (id == clientCommunication.getInfo().getId()) {
//            view.reset(); // todo handle disconnect
        } else {
            clientCommunication.unbindBody(id);
        }
    }
}

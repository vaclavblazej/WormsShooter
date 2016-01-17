package cz.spacks.worms.controller.comunication.server.actions.impl;

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
//            worldService.reset(); // todo handle disconnect
        } else {
            clientCommunication.unbindBody(id);
        }
    }
}

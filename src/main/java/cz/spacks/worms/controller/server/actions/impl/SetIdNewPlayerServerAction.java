package cz.spacks.worms.controller.server.actions.impl;

import cz.spacks.worms.controller.client.ClientCommunication;
import cz.spacks.worms.controller.server.actions.ActionServer;
import cz.spacks.worms.controller.PlayerInfo;

/**
 *
 */
public class SetIdNewPlayerServerAction extends ActionServer {

    public SetIdNewPlayerServerAction(int id) {
        super(id);
    }

    public void perform() {
        ClientCommunication.getInstance().setInfo(new PlayerInfo(id));
    }
}

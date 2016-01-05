package cz.spacks.worms.view.server.actions.impl;

import cz.spacks.worms.view.client.ClientCommunication;
import cz.spacks.worms.view.server.actions.ActionServer;
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

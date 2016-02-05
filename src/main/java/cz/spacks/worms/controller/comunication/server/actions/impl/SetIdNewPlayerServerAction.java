package cz.spacks.worms.controller.comunication.server.actions.impl;

import cz.spacks.worms.controller.PlayerInfo;
import cz.spacks.worms.controller.comunication.server.actions.ActionServer;

/**
 *
 */
public class SetIdNewPlayerServerAction extends ActionServer {

    public SetIdNewPlayerServerAction(int id) {
        super(id);
    }

    public void perform() {
        clientCommunication.setInfo(new PlayerInfo(id));
    }
}

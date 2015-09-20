package cz.spacks.worms.server.actions.impl;

import cz.spacks.worms.client.ClientCommunication;
import cz.spacks.worms.server.actions.ActionServer;
import cz.spacks.worms.utilities.PlayerInfo;

/**
 * @author Václav Blažej
 */
public class SetIdNewPlayerServerAction extends ActionServer {

    public SetIdNewPlayerServerAction(int id) {
        super(id);
    }

    public void perform() {
        ClientCommunication.getInstance().setInfo(new PlayerInfo(id));
    }
}

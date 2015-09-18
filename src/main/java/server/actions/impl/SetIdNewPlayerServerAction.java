package server.actions.impl;

import client.ClientCommunication;
import server.actions.ActionServer;
import utilities.PlayerInfo;

/**
 * @author V�clav Bla�ej
 */
public class SetIdNewPlayerServerAction extends ActionServer {

    public SetIdNewPlayerServerAction(int id) {
        super(id);
    }

    public void perform() {
        ClientCommunication.getInstance().setInfo(new PlayerInfo(id));
    }
}

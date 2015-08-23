package client.actions.impl;

import client.actions.ActionClient;
import server.ServerCommunication;
import server.actions.impl.SendChatServerAction;

/**
 * @author V�clav Bla�ej
 */
public class SendChatAction extends ActionClient {

    private String str;

    public SendChatAction(String str) {
        this.str = str;
    }

    @Override
    public void perform() {
        ServerCommunication.getInstance().broadcast(new SendChatServerAction(id, str));
    }
}

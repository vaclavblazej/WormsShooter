package cz.spacks.worms.controller.comunication.client.actions.impl;

import cz.spacks.worms.controller.comunication.client.actions.ActionClient;
import cz.spacks.worms.controller.comunication.server.ServerCommunication;
import cz.spacks.worms.controller.comunication.server.actions.impl.SendChatServerAction;

/**
 *
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

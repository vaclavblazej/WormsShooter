package cz.spacks.worms.view.client.actions.impl;

import cz.spacks.worms.view.client.actions.ActionClient;
import cz.spacks.worms.view.server.ServerCommunication;
import cz.spacks.worms.view.server.actions.impl.SendChatServerAction;

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

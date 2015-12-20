package cz.spacks.worms.server.actions.impl;

import cz.spacks.worms.client.ChatLog;
import cz.spacks.worms.server.actions.ActionServer;

/**
 *
 */
public class SendChatServerAction extends ActionServer {

    private String str;

    public SendChatServerAction(int id, String str) {
        super(id);
        this.str = str;
    }

    public void perform() {
        ChatLog.getInstance().log(id + " " + str);
    }
}

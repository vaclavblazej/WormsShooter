package cz.spacks.worms.controller.comunication.server.actions.impl;

import cz.spacks.worms.view.component.ChatLog;
import cz.spacks.worms.controller.comunication.server.actions.ActionServer;

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

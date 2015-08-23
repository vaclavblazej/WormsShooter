package server.actions.impl;

import server.actions.ActionServer;

/**
 * @author Skarab
 */
public class SendChatServerAction extends ActionServer {

    private String str;

    public SendChatServerAction(int id, String str) {
        super(id);
        this.str = str;
    }

    public void perform() {
        view.logChat(id + " " + str);
    }
}

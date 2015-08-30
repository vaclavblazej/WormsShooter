package server.actions.impl;

import client.ChatLog;
import server.actions.ActionServer;

/**
 * @author V�clav Bla�ej
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

package cz.spacks.worms.view.component;

import java.util.LinkedList;

/**
 *
 */
public class ChatLog {

    private LinkedList<String> chat;

    public ChatLog() {
        chat = new LinkedList<>();
        chat.add("Hello world!");
    }

    public void log(String log) {
        if (chat.size() > 8) {
            chat.removeFirst();
        }
        chat.add(log);
    }

    public LinkedList<String> getChat() {
        return chat;
    }
}

package client;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.LinkedList;

/**
 * @author Václav Blažej
 */
public class ChatLog {

    private LinkedList<String> chat;

    public ChatLog() {
        chat = new LinkedList<>();
    }

    public void log(String log) {
        if (chat.size() > 8) {
            chat.removeFirst();
        }
        chat.add(log);
    }

    public void draw(Graphics2D g) {
        g.setTransform(new AffineTransform());
        g.setBackground(Color.BLACK);
        g.setColor(Color.WHITE);
        int x = 20;
        int y = 20;
        for (String string : chat) {
            g.drawString(string, x, y);
            y += 20;
        }
    }
}

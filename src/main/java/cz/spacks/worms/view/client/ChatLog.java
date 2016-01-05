package cz.spacks.worms.view.client;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.LinkedList;

/**
 *
 */
public class ChatLog extends JPanel {

    private static ChatLog instance;

    public static ChatLog getInstance() {
        if (instance == null) instance = new ChatLog();
        return instance;
    }

    private LinkedList<String> chat;
    private JList<String> list;

    public ChatLog() {
        setBackground(Color.GREEN);
        chat = new LinkedList<>();
        list = new JList<>();
        list.setBackground(new Color(100, 100, 100, 10));
    }

    public void log(String log) {
        if (chat.size() > 8) {
            chat.removeFirst();
        }
        chat.add(log);
        list.add(log, new JLabel(log));
    }

//    public void draw(Graphics2D g) {
//        g.setTransform(new AffineTransform());
//        g.setBackground(Color.BLACK);
//        g.setColor(Color.WHITE);
//        int x = 20;
//        int y = 20;
//        for (String string : chat) {
//            g.drawString(string, x, y);
//            y += 20;
//        }
//    }

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        final Graphics2D g = (Graphics2D) graphics;
        g.setTransform(new AffineTransform());
        int x = 20;
        int y = 20;
        for (String string : chat) {
            g.drawString(string, x, y);
            y += 20;
        }
    }

    public JList<String> getList() {
        return list;
    }
}

package cz.spacks.worms.view.component;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.LinkedList;

/**
 *
 */
public class ChatLog extends JPanel {

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

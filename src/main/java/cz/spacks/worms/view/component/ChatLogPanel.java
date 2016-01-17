package cz.spacks.worms.view.component;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;

/**
 *
 */
public class ChatLogPanel extends JPanel {

    private ChatLog chatLog;
    private JList<String> list;

    public ChatLogPanel() {
        setBackground(Color.GREEN);
        list = new JList<>();
        chatLog = new ChatLog();
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        final Graphics2D g = (Graphics2D) graphics;
        g.setTransform(new AffineTransform());
        int x = 20;
        int y = 20;
        for (String string : chatLog.getChat()) {
            g.drawString(string, x, y);
            y += 20;
        }
    }

    public JList<String> getList() {
        return list;
    }

    public void setChatLog(ChatLog chatLog) {
        this.chatLog = chatLog;
    }
}

package main.windows;

import server.ServerView;
import utilities.properties.Message;

import javax.swing.*;
import java.awt.*;

/**
 * @author Václav Blažej
 */
public class ServerFrame extends JFrame {
    private JPanel rootPanel;
    private JPanel serverView;
    private JButton shutDownServerButton;

    public ServerFrame() {
        super(Message.SERVER_WINDOW_TITLE.value());

        setContentPane(rootPanel);
        pack();
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        setMinimumSize(new Dimension(400, 300));
//        setLocationRelativeTo(null);
        setLocation(10, 10);
    }

    private void createUIComponents() {
        serverView = ServerView.getInstance();
    }
}

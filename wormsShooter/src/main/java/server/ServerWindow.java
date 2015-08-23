package server;

import utilities.properties.Message;

import javax.swing.*;
import java.awt.*;

/**
 * @author Skarab
 */
public class ServerWindow extends JFrame {

    public ServerWindow() {
        super(Message.SERVER_WINDOW_TITLE.cm());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setResizable(false);

        setLayout(new BorderLayout());
        add(ServerView.getInstance(), BorderLayout.CENTER);
        pack();
        //setLocationRelativeTo(null);
        setLocation(70, 10);
    }
}

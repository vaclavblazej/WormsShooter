package server;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import utilities.properties.Message;

/**
 *
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
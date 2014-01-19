package server;

import java.awt.BorderLayout;
import javax.swing.JFrame;

/**
 *
 * @author Skarab
 */
public class ServerWindow extends JFrame {

    private static ServerPanel gamePlane;

    public ServerWindow(String title) {
        super(title);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setResizable(false);

        setLayout(new BorderLayout());
        gamePlane = new ServerPanel();
        add(gamePlane, BorderLayout.CENTER);
        pack();
        //setLocationRelativeTo(null);
        setLocation(70, 10);
    }
}

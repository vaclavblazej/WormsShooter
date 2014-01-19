package client;

import java.awt.BorderLayout;
import javax.swing.JFrame;

/**
 *
 * @author Skarab
 */
public class GameWindow extends JFrame {

    private static MainPanel gamePlane;

    public GameWindow(String title) {
        super(title);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setResizable(false);

        setLayout(new BorderLayout());
        gamePlane = new MainPanel();
        add(gamePlane, BorderLayout.CENTER);
        pack();
        //setLocationRelativeTo(null);
        setLocation(500, 10);
    }
}

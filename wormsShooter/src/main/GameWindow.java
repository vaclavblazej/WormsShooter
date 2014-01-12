package main;

import java.awt.BorderLayout;
import javax.swing.JFrame;

/**
 *
 * @author Skarab
 */
public class GameWindow extends JFrame {

    private static MainPanel gamePlane;

    public GameWindow() {
        super("Cervi 1.0");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setResizable(false);

        gamePlane = new MainPanel();
        setLayout(new BorderLayout());
        gamePlane.setFocusable(true);
        add(gamePlane, BorderLayout.CENTER);
        pack();

        gamePlane.startMoving();
        setLocationRelativeTo(null);
    }
}

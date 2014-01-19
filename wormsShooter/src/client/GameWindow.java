package client;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import utilities.Message;

/**
 *
 * @author Skarab
 */
public class GameWindow extends JFrame {

    private static MainPanel gamePlane;
    private static GameWindow instance;

    public static GameWindow getInstance() {
        if (instance == null) {
            instance = new GameWindow(Message.Client_window_title.cm());
        }
        return instance;
    }

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

    public void showError(String message) {
        JOptionPane.showMessageDialog(this, message);
    }
}

package client;

import client.menu.GameLauncher;
import client.menu.GameWindowItemBar;
import client.menu.GameWindowMenu;
import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JToolBar;
import main.Main;
import utilities.properties.Message;

/**
 *
 * @author Skarab
 */
public class ClientWindow extends JFrame {

    private static ClientWindow instance;

    public static ClientWindow getInstance() {
        if (instance == null) {
            instance = new ClientWindow(Message.CLIENT_WINDOW_TITLE.cm());
        }
        return instance;
    }
    private ClientView gamePlane;
    private JMenuBar menuBar;
    private JToolBar itemBar;

    public ClientWindow(String title) {
        super(title);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setResizable(false);
        setLayout(new BorderLayout());

        menuBar = new GameWindowMenu();
        setJMenuBar(menuBar);

        itemBar = GameWindowItemBar.getInstance();
        add(itemBar, BorderLayout.SOUTH);

        gamePlane = ClientView.getInstance();
        add(gamePlane, BorderLayout.CENTER);
        pack();
        //setLocationRelativeTo(null);
        setLocation(500, 10);
    }

    public void launch() {
        new GameLauncher(this);
    }

    public void showError(Exception ex) {
        JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.WARNING_MESSAGE);
        Main.exit();
    }
}

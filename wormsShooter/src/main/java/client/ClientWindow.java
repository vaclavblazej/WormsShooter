package client;

import client.menu.GameLauncher;
import client.menu.GameWindowChat;
import client.menu.GameWindowItemBar;
import client.menu.GameWindowMenu;
import main.Application;
import utilities.properties.Message;

import javax.swing.*;
import java.awt.*;

/**
 * @author Václav Blažej
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
    private JPanel bottomPanel;
    private JPanel chatBar;
    private JToolBar itemBar;

    public ClientWindow(String title) {
        super(title);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setResizable(false);
        setLayout(new BorderLayout());

        menuBar = new GameWindowMenu();
        setJMenuBar(menuBar);

        chatBar = GameWindowChat.getInstance();
        itemBar = GameWindowItemBar.getInstance();
        bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(chatBar, BorderLayout.WEST);
        bottomPanel.add(itemBar, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

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
        Application.exit();
    }
}

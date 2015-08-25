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
        if (instance == null) instance = new ClientWindow(Message.CLIENT_WINDOW_TITLE.value());
        return instance;
    }

    public ClientWindow(String title) {
        super(title);
        ClientView gamePlane;
        JMenuBar menuBar;
        JPanel bottomPanel;
        JPanel chatBar;
        JToolBar itemBar;

        super.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        super.setVisible(true);
        super.setResizable(false);
        super.setLayout(new BorderLayout());

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
        setLocationRelativeTo(null);
//        setLocation(500, 10);
    }

    public void launch() {
        new GameLauncher(this);
    }

    public void showError(Exception ex) {
        JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.WARNING_MESSAGE);
        Application.exit();
    }
}

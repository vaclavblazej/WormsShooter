package main;

import client.ClientCommunication;
import client.ClientView;
import client.ClientWindow;
import server.ServerCommunication;
import server.ServerView;
import server.ServerWindow;

import javax.swing.*;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Václav Blažej
 */
public class Application {

    private static final Logger logger = Logger.getLogger(Application.class.getName());

    private static boolean serverOnline = false;
    public static final double RATIO = 1;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                startGame();
            }
        });
    }

    public static void startGame() {
        ClientWindow.getInstance().launch();
    }

    public static void startClient(String address, String socket) {
        ClientCommunication.getInstance().init(address, socket);
    }

    public static void startClientView() {
        ClientView.getInstance().init();
    }

    public static void startServer(int port) {
        if (!serverOnline) {
            new ServerWindow();
            try {
                new ServerCommunication(port);
            } catch (IOException ex) {
                logger.log(Level.SEVERE, null, ex);
            }
            serverOnline = true;
        }
    }

    public static void exit() {
        if (serverOnline) {
            ServerView.getInstance().save();
        }
        System.exit(0);
    }
}

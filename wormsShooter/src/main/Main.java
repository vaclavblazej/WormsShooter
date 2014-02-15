package main;

import client.ClientCommunication;
import client.ClientView;
import client.ClientWindow;
import javax.swing.SwingUtilities;
import server.ServerCommunication;
import server.ServerView;
import server.ServerWindow;

/**
 *
 * @author Skarab
 */
public class Main {

    private static boolean server = false;
    public static final double RATIO = 20;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
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
        ClientView.getInstance().init();
    }

    public static void startServer(int port) {
        if (server == false) {
            ServerCommunication.getInstance().init(port);
            new ServerWindow();
            server = true;
        }
    }

    public static void exit() {
        if (server) {
            ServerView.getInstance().save();
        }
        System.exit(0);
    }
}

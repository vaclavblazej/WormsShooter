package main;

import client.ClientCommunication;
import client.ClientView;
import client.ClientWindow;
import communication.backend.utilities.SLoggerService;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.SwingUtilities;
import server.ServerCommunication;
import server.ServerView;
import server.ServerWindow;

/**
 *
 * @author Václav Blažej
 */
public class Main {

    private static boolean serverOnline = false;
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
    }

    public static void startClientView() {
        ClientView.getInstance().init();
    }

    public static void startServer(int port) {
        if (serverOnline == false) {
            new ServerWindow();
            SLoggerService.setLogger(new SLoggerService.SLoggerPrint());
            try {
                new ServerCommunication(port);
            } catch (IOException ex) {
                Logger.getLogger(ServerCommunication.class.getName()).log(Level.SEVERE, null, ex);
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

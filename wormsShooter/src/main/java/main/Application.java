package main;

import client.ClientCommunication;
import main.windows.MainFrame;
import server.ServerCommunication;
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

    public static final double RATIO = 20;
    public static final String version = "1.1.0-SNAPSHOT";

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainFrame::new);
    }

    public static void startClient(String address, String socket) {
        ClientCommunication.getInstance().init(address, socket);
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
//            ServerView.getInstance().save();
            serverOnline = false;
        }
        System.exit(0);
    }
}

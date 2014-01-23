package main;

import client.GameWindow;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.SwingUtilities;
import server.ServerCommunication;
import server.ServerPanel;
import server.ServerWindow;

/**
 *
 * @author Skarab
 */
public class Main {

    private static boolean server = false;

    public static void main(String[] args) {
        try {
            ServerCommunication.getInstance().init();
        } catch (RemoteException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                startGame();
            }
        });
    }

    public static void startGame() {
        GameWindow.getInstance().launch();
    }

    public static void startServer() {
        new ServerWindow();
        server = true;
    }

    public static void exit() {
        if (server) {
            ServerPanel.getInstance().save();
        }
        System.exit(0);
    }
}

package main;

import client.GameLauncher;
import client.GameWindow;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.SwingUtilities;
import server.ServerCommunication;
import server.ServerWindow;
import utilities.Message;

/**
 *
 * @author Skarab
 */
public class Main {

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
        new ServerWindow(Message.Server_window_title.cm());
    }

    public static void exit() {
        System.exit(0);
    }
}

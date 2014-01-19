package main;

import client.ClientCommunication;
import client.GameWindow;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
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
        try {
            ClientCommunication.getInstance().init("localhost");
        } catch (NotBoundException | MalformedURLException | RemoteException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                startGame();
            }
        });
    }

    public static void startMenu() {
    }

    public static void startGame() {
        new ServerWindow(Message.Server_window_title.cm());
        new GameWindow(Message.Client_window_title.cm());
    }
}

package main;

import client.ClientWindow;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;
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
        /*GameWindow.getInstance();
         Main.startServer();
         try {
         ClientCommunication.getInstance().init("localhost", "4242");
         } catch (NotBoundException ex) {
         Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
         } catch (MalformedURLException ex) {
         Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
         } catch (RemoteException ex) {
         Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
         }
         MainPanel.getInstance().init();*/
    }

    public static void startServer(int port) {
        try {
            ServerCommunication.getInstance().init(port);
        } catch (RemoteException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        new ServerWindow();
        server = true;
    }

    public static void exit() {
        if (server) {
            ServerView.getInstance().save();
        }
        System.exit(0);
    }
}

package main;

import client.GameWindow;
import javax.swing.SwingUtilities;
import server.ServerWindow;
import utilities.Message;

/**
 *
 * @author Skarab
 */
public class Main {

    public static void main(String[] args) {
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

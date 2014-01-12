package main;

import javax.swing.SwingUtilities;

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
        new GameWindow();
    }
}

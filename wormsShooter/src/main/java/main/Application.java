package main;

import main.windows.MainFrame;

import javax.swing.*;
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

    public static void exit() {
//        ServerView.getInstance().save();
        System.exit(0);
    }
}

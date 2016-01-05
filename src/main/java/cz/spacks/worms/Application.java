package cz.spacks.worms;

import cz.spacks.worms.view.windows.MainFrame;

import javax.swing.*;

/**
 *
 */
public class Application {

    public static final int RATIO = 20; // todo should define zoom in views
    public static final int BLOCK_SIZE = 32;
    public static final String version = "1.1.0-SNAPSHOT";

    public static void main(String[] args) {
//        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        SwingUtilities.invokeLater(MainFrame::new);
    }

    public static void exit() {
//        ServerView.getInstance().save();
        System.exit(0);
    }

    public static void error(Exception ex){
        ex.printStackTrace();
    }
}

package cz.spacks.worms.view.component;

import javax.swing.*;
import java.awt.*;

/**
 *
 */
public class GameWindowItemBar extends JToolBar {

    private static GameWindowItemBar instance;

    public static GameWindowItemBar getInstance() {
        if (instance == null) {
            instance = new GameWindowItemBar();
        }
        return instance;
    }

    public GameWindowItemBar() {
        setPreferredSize(new Dimension(500, 26));
        setFloatable(false);
    }

    public void clearBar() {
        removeAll();
        repaint();
    }
}

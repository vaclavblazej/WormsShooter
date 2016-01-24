package cz.spacks.worms.view.component;

import javax.swing.*;
import java.awt.*;

/**
 *
 */
public class GameWindowItemBar extends JToolBar {

    public GameWindowItemBar() {
        setPreferredSize(new Dimension(500, 26));
        setFloatable(false);
    }

    public void clearBar() {
        removeAll();
        repaint();
    }
}

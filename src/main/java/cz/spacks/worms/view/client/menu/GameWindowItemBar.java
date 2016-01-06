package cz.spacks.worms.view.client.menu;

import cz.spacks.worms.model.objects.Inventory;

import javax.swing.*;
import java.awt.*;
import java.util.List;

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

    public void refreshBar(Inventory itm) {
        clearBar();
        List<JButton> toolbar = itm.generateToolbar();
        for (JButton jButton : toolbar) {
            add(jButton);
        }
    }
}
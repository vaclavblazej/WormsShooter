package client.menu;

import java.awt.Dimension;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JToolBar;
import objects.items.InventoryTableModel;

/**
 *
 * @author Pajcak
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
        setPreferredSize(new Dimension(800, 26));
        setFloatable(false);
    }

    public void clearBar() {
        removeAll();
        repaint();
    }

    public void refreshBar(InventoryTableModel itm) {
        clearBar();
        List<JButton> toolbar = itm.generateToolbar();
        for (JButton jButton : toolbar) {
            add(jButton);
        }
    }
}

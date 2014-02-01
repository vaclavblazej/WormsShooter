package client.menu;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JToolBar;
import objects.items.ComponentTableModel;

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

    public void refreshBar(ComponentTableModel ctm) {
        clearBar();
        for (int i = 0; i < ctm.getRowCount(); i++) {
            if (ctm.isUsable(i)) {
                JButton btn = new JButton(new AbstractAction() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        //todo - set this item as chosen. highlight this button
                    }
                });
                btn.setText(ctm.getValueAt(i, 0).toString() + ": " + ctm.getValueAt(i, 1).toString());
                btn.setFocusable(false);
                add(btn);
            }
        }
    }
}

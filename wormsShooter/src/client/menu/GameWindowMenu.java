package client.menu;

import client.GameWindow;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import javax.swing.AbstractAction;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;
import main.Main;
import utilities.Message;

/**
 *
 * @author Skarab
 */
public class GameWindowMenu extends JMenuBar {

    public GameWindowMenu() {
        JMenu menu = new JMenu(Message.Menu_main.cm());
        JMenuItem item = new JMenuItem();
        item.setAction(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new SettingsDialog(GameWindow.getInstance());
            }
        });
        item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, 0));
        item.setText(Message.Menu_settings.cm());
        menu.add(item);
        item = new JMenuItem();
        item.setAction(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new InventoryDialog(GameWindow.getInstance());
            }
        });
        item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_I, 0));
        item.setText(Message.Menu_inventory.cm());
        menu.add(item);
        item = new JMenuItem();
        item.setAction(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Main.exit();
            }
        });
        item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F4, InputEvent.ALT_DOWN_MASK));
        item.setText(Message.Menu_exit.cm());
        menu.add(item);
        add(menu);

        menu = new JMenu(Message.Menu_connection.cm());
        item = new JMenuItem();
        item.setAction(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GameWindow.getInstance().launch();
            }
        });
        item.setText(Message.Menu_connect.cm());
        menu.add(item);
        item = new JMenuItem();
        item.setAction(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
            }
        });
        item.setText(Message.Menu_disconnect.cm());
        menu.add(item);
        add(menu);
    }
}

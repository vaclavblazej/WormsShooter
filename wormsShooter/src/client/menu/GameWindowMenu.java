package client.menu;

import client.ClientCommunication;
import client.ClientWindow;
import communication.client.DisconnectAction;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import javax.swing.AbstractAction;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;
import main.Main;
import utilities.properties.Message;

/**
 *
 * @author Skarab
 */
public class GameWindowMenu extends JMenuBar {

    public GameWindowMenu() {
        JMenu menu = new JMenu(Message.MENU_MAIN.cm());
        JMenuItem item = new JMenuItem();
        item.setAction(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new SettingsDialog(ClientWindow.getInstance());
            }
        });
        item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, 0));
        item.setText(Message.MENU_SETTINGS.cm());
        menu.add(item);
        item = new JMenuItem();
        item.setAction(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new InventoryDialog(ClientWindow.getInstance());
            }
        });
        item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_I, 0));
        item.setText(Message.MENU_INVENTORY.cm());
        menu.add(item);
        item = new JMenuItem();
        item.setAction(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Main.exit();
            }
        });
        item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F4, InputEvent.ALT_DOWN_MASK));
        item.setText(Message.MENU_EXIT.cm());
        menu.add(item);
        add(menu);

        menu = new JMenu(Message.MENU_CONNECTION.cm());
        item = new JMenuItem();
        item.setAction(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ClientWindow.getInstance().launch();
            }
        });
        item.setText(Message.MENU_CONNECT.cm());
        menu.add(item);
        item = new JMenuItem();
        item.setAction(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ClientCommunication.getInstance().send(new DisconnectAction());
            }
        });
        item.setText(Message.MENU_DISCONNECT.cm());
        menu.add(item);
        add(menu);
    }
}

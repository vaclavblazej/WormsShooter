package client.menu;

import client.ClientCommunication;
import client.ClientWindow;
import client.actions.impl.DisconnectAction;
import main.Application;
import utilities.properties.Message;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

/**
 * @author Václav Blažej
 */
public class GameWindowMenu extends JMenuBar {

    public GameWindowMenu() {
        JMenu menu = new JMenu(Message.MENU_MAIN.value());
        JMenuItem item = new JMenuItem();
        item.setAction(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new SettingsDialog(ClientWindow.getInstance());
            }
        });
        item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, 0));
        item.setText(Message.MENU_SETTINGS.value());
        menu.add(item);
        item = new JMenuItem();
        item.setAction(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new InventoryDialog(ClientWindow.getInstance());
            }
        });
        item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_I, 0));
        item.setText(Message.MENU_INVENTORY.value());
        menu.add(item);
        item = new JMenuItem();
        item.setAction(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Application.exit();
            }
        });
        item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F4, InputEvent.ALT_DOWN_MASK));
        item.setText(Message.MENU_EXIT.value());
        menu.add(item);
        add(menu);

        menu = new JMenu(Message.MENU_CONNECTION.value());
        item = new JMenuItem();
        item.setAction(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ClientWindow.getInstance();
            }
        });
        item.setText(Message.MENU_CONNECT.value());
        menu.add(item);
        item = new JMenuItem();
        item.setAction(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ClientCommunication.getInstance().send(new DisconnectAction());
            }
        });
        item.setText(Message.MENU_DISCONNECT.value());
        menu.add(item);
        add(menu);
    }
}

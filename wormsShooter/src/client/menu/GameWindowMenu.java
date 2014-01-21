package client.menu;

import client.GameWindow;
import client.SettingsDialog;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import main.Main;

/**
 *
 * @author Skarab
 */
public class GameWindowMenu extends JMenuBar {

    public GameWindowMenu() {
        JMenu menu = new JMenu("Main");
        JMenuItem item = new JMenuItem();
        item.setAction(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new SettingsDialog(GameWindow.getInstance());
            }
        });
        item.setText("Settings");
        menu.add(item);
        add(menu);
        item = new JMenuItem();
        item.setAction(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Main.exit();
            }
        });
        item.setText("Exit");
        menu.add(item);
        add(menu);

        menu = new JMenu("Connection");
        item = new JMenuItem();
        item.setAction(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GameWindow.getInstance().launch();
            }
        });
        item.setText("Connect");
        menu.add(item);
        item = new JMenuItem();
        item.setAction(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
            }
        });
        item.setText("Disconnect");
        menu.add(item);
        add(menu);
    }
}

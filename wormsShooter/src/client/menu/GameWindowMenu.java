package client.menu;

import client.GameWindow;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
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
        item.setText(Message.Menu_settings.cm());
        menu.add(item);
        add(menu);
        item = new JMenuItem();
        item.setAction(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Main.exit();
            }
        });
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

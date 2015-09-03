package client.menu;

import client.ClientCommunication;
import client.actions.impl.SendChatAction;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * @author Václav Blažej
 */
public class GameWindowChat extends JPanel {

    private static GameWindowChat instance;

    public static GameWindowChat getInstance() {
        if (instance == null) {
            instance = new GameWindowChat();
        }
        return instance;
    }

    private JTextField field;
    private JButton button;

    public GameWindowChat() {
        field = new JTextField();
        field.setPreferredSize(new Dimension(150, 26));
        button = new JButton(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                send();
            }
        });
        button.setText("Send");
        button.setFont(new Font("sansserif", Font.PLAIN, 8));
        button.setFocusable(false);
        add(field);
        add(button);
    }

    public void send() {
        ClientCommunication.getInstance().send(new SendChatAction(field.getText()));
        field.setText("");
    }
}

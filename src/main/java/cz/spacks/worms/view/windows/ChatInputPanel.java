package cz.spacks.worms.view.windows;

import cz.spacks.worms.controller.client.ClientCommunication;
import cz.spacks.worms.view.client.ClientView;
import cz.spacks.worms.controller.client.actions.impl.SendChatAction;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 *
 */
public class ChatInputPanel extends JPanel {

    private static ChatInputPanel instance;

    public static ChatInputPanel getInstance() {
        if (instance == null) instance = new ChatInputPanel();
        return instance;
    }

    private JTextField field;
    private JButton button;

    public ChatInputPanel() {
        field = new JTextField();
        field.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyChar() == '\n') send();
            }
        });
        button = new CustomButton(e -> send());
        button.setFont(new Font("sansserif", Font.PLAIN, 8));
        button.setFocusable(false);
    }

    public void send() {
        ClientCommunication.getInstance().send(new SendChatAction(field.getText()));
        field.setText("");
        ClientView.getInstance().grabFocus();
    }

    public JTextField getField() {
        return field;
    }

    public JButton getButton() {
        return button;
    }
}

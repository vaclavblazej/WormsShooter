package cz.spacks.worms.view.windows;

import cz.spacks.worms.controller.comunication.client.ClientCommunication;
import cz.spacks.worms.view.component.FocusGrabber;
import cz.spacks.worms.view.views.ClientView;
import cz.spacks.worms.controller.comunication.client.actions.impl.SendChatAction;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 *
 */
public class ChatInputPanel extends JPanel implements FocusGrabber{

    private JTextField field;
    private JButton button;
    private FocusGrabber focusGrabber = FocusGrabber.NULL;

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
        focusGrabber.focus();
    }

    public void setFocusGrabber(FocusGrabber focusGrabber) {
        this.focusGrabber = focusGrabber;
    }

    public JTextField getField() {
        return field;
    }

    public JButton getButton() {
        return button;
    }

    @Override
    public void focus() {
        this.grabFocus();
    }
}

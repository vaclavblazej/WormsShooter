package main.windows;

import utilities.SoundManager;
import utilities.properties.Sounds;

import javax.swing.*;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

/**
 * @author Václav Blažej
 */
public class CustomButton extends JButton {
    {
        ChangeListener cl = e -> {
            ButtonModel bm = getModel();
            if (bm.isRollover()) {
                grabFocus();
                activateFocusVisuals();
            } else {
                removeFocusVisuals();
            }
        };

        FocusListener fl = new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                activateFocusVisuals();
            }

            @Override
            public void focusLost(FocusEvent e) {
                removeFocusVisuals();
            }
        };
        addChangeListener(cl);
        addFocusListener(fl);
    }

    public CustomButton() {
    }

    public CustomButton(FunctionalAction a) {
        super(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SoundManager.playSound(Sounds.BUTTON_TICK);
                a.actionPerformed(e);
            }
        });
    }

    public void activateFocusVisuals() {
        setForeground(Color.BLUE);
    }

    public void removeFocusVisuals() {
        setForeground(Color.BLACK);
    }
}

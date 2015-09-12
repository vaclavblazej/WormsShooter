package main.windows;

import utilities.SoundManager;
import utilities.properties.Sounds;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * @author V�clav Bla�ej
 */
public class CustomButton extends JButton {

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
}

package cz.spacks.worms.view.windows;

import cz.spacks.worms.controller.SoundManager;
import cz.spacks.worms.controller.properties.Sounds;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 *
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
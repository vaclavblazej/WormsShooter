package cz.spacks.worms.main.windows;

import cz.spacks.worms.utilities.SoundManager;
import cz.spacks.worms.utilities.properties.Sounds;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * @author Václav Blažej
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

package cz.spacks.worms.view.windows;

import javax.swing.*;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

/**
 *
 */
public class CustomSoundButton extends CustomButton {
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

    public CustomSoundButton() {
    }

    public CustomSoundButton(FunctionalAction a) {
        super(a);
    }

    public void activateFocusVisuals() {
        setForeground(Color.BLUE);
    }

    public void removeFocusVisuals() {
        setForeground(Color.BLACK);
    }
}

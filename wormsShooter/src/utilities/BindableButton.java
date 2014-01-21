package utilities;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.AbstractAction;
import javax.swing.JButton;

/**
 *
 * @author Skarab
 */
public class BindableButton extends JButton implements KeyListener {

    private BindableButton me;

    public BindableButton(String text) {
        super();
        me = this;
        setAction(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addKeyListener(me);
            }
        });
        setText(text);
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        setText("" + e.getKeyCode());
        removeKeyListener(me);
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}

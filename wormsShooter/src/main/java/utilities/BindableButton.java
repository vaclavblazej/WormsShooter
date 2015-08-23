package utilities;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Button which listens on keyboard when pressed and changes its text accordingly.
 *
 * @author V�clav Bla�ej
 */
public class BindableButton extends JButton implements KeyListener {

    private BindableButton me;
    private int code;

    public BindableButton(int code) {
        super();
        this.code = code;
        me = this;
        setAction(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addKeyListener(me);
            }
        });
        setText(KeyEvent.getKeyText(code));
    }

    public int getKeyCode() {
        return code;
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() != KeyEvent.VK_ESCAPE) {
            code = e.getKeyCode();
            setText(KeyEvent.getKeyText(code));
        }
        removeKeyListener(me);
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}

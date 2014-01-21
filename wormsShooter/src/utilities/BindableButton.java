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
        setText("" + e.getKeyChar());
        code = e.getKeyCode();
        removeKeyListener(me);
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}

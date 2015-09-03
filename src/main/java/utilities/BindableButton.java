package utilities;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Button which listens on keyboard when pressed and changes its text accordingly.
 *
 * @author Václav Blažej
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
                setActive();
            }
        });
        refreshText();
    }

    public void setActive(){
        addKeyListener(me);
        setBackground(Color.GREEN);
    }

    public void setInactive(){
        removeKeyListener(me);
        setBackground(Color.LIGHT_GRAY);
    }

    public void refreshText(){
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
            refreshText();
        }
        setInactive();
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}

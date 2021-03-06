package cz.spacks.worms.view;

import cz.spacks.worms.controller.properties.ControlsEnum;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Button which listens on keyboard when pressed and changes its text accordingly.
 */
public class BindableButton extends JButton implements KeyListener {

    private BindableButton me;
    private int code;
    private ControlsEnum control;

    public BindableButton(ControlsEnum control, int code) {
        super();
        this.control = control;
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

    public void setActive() {
        addKeyListener(me);
        setBackground(Color.GREEN);
    }

    public void setInactive() {
        removeKeyListener(me);
        setBackground(Color.LIGHT_GRAY);
    }

    public ControlsEnum getControl() {
        return control;
    }

    public void refreshText() {
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

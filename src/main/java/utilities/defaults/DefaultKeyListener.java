package utilities.defaults;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

public interface DefaultKeyListener extends KeyListener {

    @Override
    default void keyTyped(KeyEvent e) {
    }

    @Override
    default void keyPressed(KeyEvent e) {
    }

    @Override
    default void keyReleased(KeyEvent e) {
    }
}

package cz.spacks.worms.utilities.defaults;

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

public interface DefaultMouseMotionListener extends MouseMotionListener {

    @Override
    default void mouseDragged(MouseEvent e) {
    }

    @Override
    default void mouseMoved(MouseEvent e) {
    }
}

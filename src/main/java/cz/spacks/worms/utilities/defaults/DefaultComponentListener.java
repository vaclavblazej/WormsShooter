package cz.spacks.worms.utilities.defaults;

import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

public interface DefaultComponentListener extends ComponentListener {

    @Override
    default void componentResized(ComponentEvent e) {
    }

    @Override
    default void componentMoved(ComponentEvent e) {
    }

    @Override
    default void componentShown(ComponentEvent e) {
    }

    @Override
    default void componentHidden(ComponentEvent e) {
    }
}

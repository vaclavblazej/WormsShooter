package cz.spacks.worms.view.component;

/**
 * Has method focus() which is used to change window focus.
 */
public interface FocusGrabber {

    void focus();

    FocusGrabber NULL = () -> {
    };
}

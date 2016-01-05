package cz.spacks.worms.controller.communication;

import cz.spacks.worms.controller.AbstractView;

import java.io.Serializable;

/**
 *
 */
public interface DeseriazibleInto<T> extends Serializable {

    T deserialize(AbstractView view);
}

package cz.spacks.worms.model.communication;

import cz.spacks.worms.controller.AbstractView;

import java.io.Serializable;

/**
 *
 */
public interface DeseriazibleInto<T> extends Serializable {

    T deserialize(AbstractView view);
}

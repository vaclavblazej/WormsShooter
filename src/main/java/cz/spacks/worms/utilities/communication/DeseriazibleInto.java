package cz.spacks.worms.utilities.communication;

import cz.spacks.worms.utilities.AbstractView;

import java.io.Serializable;

/**
 *
 */
public interface DeseriazibleInto<T> extends Serializable {

    T deserialize(AbstractView view);
}

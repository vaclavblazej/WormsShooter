package utilities.communication;

import utilities.AbstractView;

import java.io.Serializable;

/**
 * @author Václav Blažej
 */
public interface DeseriazibleInto<T> extends Serializable {

    T deserialize(AbstractView view);
}

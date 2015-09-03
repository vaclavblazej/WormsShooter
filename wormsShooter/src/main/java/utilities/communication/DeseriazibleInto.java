package utilities.communication;

import utilities.AbstractView;

import java.io.Serializable;

/**
 * @author V�clav Bla�ej
 */
public interface DeseriazibleInto<T> extends Serializable {

    T deserialize(AbstractView view);
}

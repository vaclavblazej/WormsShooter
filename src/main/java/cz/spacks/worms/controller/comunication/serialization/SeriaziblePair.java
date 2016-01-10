package cz.spacks.worms.controller.comunication.serialization;

import java.io.Serializable;

/**
 *
 */
public interface SeriaziblePair<A, B> extends Serializable {

    B serialize(A a);

    A deserialize();
}

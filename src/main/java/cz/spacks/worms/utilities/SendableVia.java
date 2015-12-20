package cz.spacks.worms.utilities;

import cz.spacks.worms.utilities.communication.DeseriazibleInto;

/**
 *
 */
public interface SendableVia<A extends SendableVia, T extends DeseriazibleInto<A>> {

    T serialize();
}

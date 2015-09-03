package utilities;

import utilities.communication.DeseriazibleInto;

/**
 * @author Václav Blažej
 */
public interface SendableVia<A extends SendableVia, T extends DeseriazibleInto<A>> {

    T serialize();
}

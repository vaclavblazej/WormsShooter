package utilities;

import utilities.communication.DeseriazibleInto;

/**
 * @author V�clav Bla�ej
 */
public interface SendableVia<A extends SendableVia, T extends DeseriazibleInto<A>> {

    T serialize();
}

package cz.spacks.worms.controller;

import cz.spacks.worms.model.communication.DeseriazibleInto;

/**
 *
 */
public interface SendableVia<A extends SendableVia, T extends DeseriazibleInto<A>> {

    T serialize();
}

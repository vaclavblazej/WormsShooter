package cz.spacks.worms.controller.events;

import cz.spacks.worms.model.map.WorldModel;

public interface WorldEvent {

    void perform(WorldModel worldModel);
}

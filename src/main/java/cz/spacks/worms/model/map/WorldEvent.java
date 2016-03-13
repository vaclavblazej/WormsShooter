package cz.spacks.worms.model.map;

import cz.spacks.worms.controller.services.WorldService;

public interface WorldEvent {

    void perform(WorldService worldService);
}

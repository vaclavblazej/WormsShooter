package cz.spacks.worms.controller.events.impl;

import cz.spacks.worms.controller.services.WorldService;
import cz.spacks.worms.model.map.WorldEvent;

import java.awt.*;

public class DamageChunkEvent implements WorldEvent {

    private Point position;

    public DamageChunkEvent(Point position) {
        this.position = position;
    }

    @Override
    public void perform(WorldService worldService) {
        worldService.damageChunk(position);
    }
}

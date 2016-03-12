package cz.spacks.worms.controller.events.impl;

import cz.spacks.worms.model.map.Chunk;
import cz.spacks.worms.model.map.WorldModel;
import cz.spacks.worms.controller.events.WorldEvent;

import java.awt.*;

public class RemoveChunkEvent implements WorldEvent {

    private Point position;

    public RemoveChunkEvent(Point position) {
        this.position = position;
    }

    @Override
    public void perform(WorldModel worldModel) {
        worldModel.getMap().addChunk(Chunk.NULL, position);
    }
}

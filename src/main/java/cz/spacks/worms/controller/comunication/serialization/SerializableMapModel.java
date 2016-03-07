package cz.spacks.worms.controller.comunication.serialization;

import cz.spacks.worms.model.map.Chunk;
import cz.spacks.worms.model.map.MapModel;
import cz.spacks.worms.model.structures.Map2D;

import java.awt.*;

/**
 *
 */
public class SerializableMapModel implements SeriaziblePair<MapModel, SerializableMapModel> {

    private Map2D<Chunk> map;
    private Dimension dimensions;

    @Override
    public SerializableMapModel serialize(MapModel mapModel) {
        map = mapModel.getMap();
        dimensions = mapModel.getDimensions();
        return this;
    }

    @Override
    public MapModel deserialize() {
        return new MapModel(dimensions, map);
    }
}

package cz.spacks.worms.controller.comunication.serialization;

import cz.spacks.worms.model.MapModel;

/**
 *
 */
public class SerializableMapModel implements SeriaziblePair<MapModel, SerializableMapModel> {

    private SerializableBufferedImage map;

    @Override
    public SerializableMapModel serialize(MapModel mapModel) {
        map = new SerializableBufferedImage().serialize(mapModel.getImage());
        return this;
    }

    @Override
    public MapModel deserialize() {
        return new MapModel(map.deserialize());
    }
}

package cz.spacks.worms.controller.communication;

import cz.spacks.worms.controller.AbstractView;
import cz.spacks.worms.controller.MapClass;

import java.awt.image.BufferedImage;

/**
 *
 */
public class SerializableMapClass implements DeseriazibleInto<MapClass> {

    private SerializableBufferedImage map;

    public SerializableMapClass(BufferedImage map) {
        this.map = new SerializableBufferedImage(map);
    }

    @Override
    public MapClass deserialize(AbstractView view) {
        return new MapClass(map.deserialize(), view);
    }
}

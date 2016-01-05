package cz.spacks.worms.utilities.communication;

import cz.spacks.worms.utilities.AbstractView;
import cz.spacks.worms.utilities.MapClass;

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

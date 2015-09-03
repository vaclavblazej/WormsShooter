package utilities.communication;

import objects.LightSource;
import utilities.AbstractView;
import utilities.MapClass;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * @author Václav Blažej
 */
public class SerializableMapClass implements DeseriazibleInto<MapClass> {

    private SerializableBufferedImage map;
    private ArrayList<LightSource> lights;

    public SerializableMapClass(BufferedImage map, ArrayList<LightSource> lights) {
        this.map = new SerializableBufferedImage(map);
        this.lights = lights;
    }

    @Override
    public MapClass deserialize(AbstractView view) {
        return new MapClass(map.deserialize(), view, lights);
    }
}

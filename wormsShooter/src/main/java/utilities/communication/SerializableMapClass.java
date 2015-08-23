package utilities.communication;

import objects.LightSource;
import utilities.AbstractView;
import utilities.MapClass;

import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * @author Václav Blažej
 */
public class SerializableMapClass implements Serializable {

    private SerializableBufferedImage map;
    private ArrayList<LightSource> lights;

    public SerializableMapClass(BufferedImage map, ArrayList<LightSource> lights) {
        this.map = new SerializableBufferedImage(map);
        this.lights = lights;
    }

    MapClass deserialize(AbstractView view) {
        return new MapClass(map.getImage(), view, lights);
    }
}

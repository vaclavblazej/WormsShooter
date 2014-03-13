package utilities.communication;

import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.util.ArrayList;
import objects.LightSource;
import utilities.AbstractView;
import utilities.MapClass;
import utilities.materials.MaterialEnum;

/**
 *
 * @author Skarab
 */
public class SerializableMapClass implements Serializable {

    private MaterialEnum[][] map;
    private ArrayList<LightSource> lights;

    public SerializableMapClass(MaterialEnum[][] map, ArrayList<LightSource> lights) {
        this.map = map;
        this.lights = lights;
    }

    MapClass deserialize(AbstractView view) {
        return new MapClass(map, view, lights);
    }
}

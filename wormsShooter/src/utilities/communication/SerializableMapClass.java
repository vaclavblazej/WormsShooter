package utilities.communication;

import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.util.ArrayList;
import objects.GraphicComponent;
import objects.LightSource;
import utilities.AbstractView;
import utilities.MapClass;

/**
 *
 * @author Skarab
 */
public class SerializableMapClass implements Serializable {

    private SerializableBufferedImage map;
    private ArrayList<LightSource> lights;
    private ArrayList<GraphicComponent> activeObjs;

    public SerializableMapClass(BufferedImage map, ArrayList<LightSource> lights, ArrayList<GraphicComponent> activeObjects) {
        this.map = new SerializableBufferedImage(map);
        this.lights = lights;
        this.activeObjs = activeObjects;
    }

    public MapClass deserialize(AbstractView view) {
        return new MapClass(map.getImage(), view, lights, activeObjs);
    }
}

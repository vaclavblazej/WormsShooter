package utilities.communication;

import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.util.ArrayList;
import objects.GraphicComponent;
import utilities.AbstractView;
import utilities.MapClass;

/**
 *
 * @author Skarab
 */
public class SerializableMapClass implements Serializable {

    private SerializableBufferedImage map;
    private ArrayList<GraphicComponent> activeObjs;

    public SerializableMapClass(BufferedImage map, ArrayList<GraphicComponent> activeObjects) {
        this.map = new SerializableBufferedImage(map);
        this.activeObjs = activeObjects;
    }

    public MapClass deserialize(AbstractView view) {
        return new MapClass(map.getImage(), view, activeObjs);
    }
}

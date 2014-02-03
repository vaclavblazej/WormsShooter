package utilities.communication;

import java.awt.image.BufferedImage;
import java.io.Serializable;
import utilities.AbstractView;
import utilities.MapClass;

/**
 *
 * @author Skarab
 */
public class SerializableMapClass implements Serializable {

    private SerializableBufferedImage map;

    public SerializableMapClass(BufferedImage map) {
        this.map = new SerializableBufferedImage(map);
    }

    MapClass deserialize(AbstractView view) {
        return new MapClass(map.getImage(), view);
    }
}

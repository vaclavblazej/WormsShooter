package objects;

import java.io.Serializable;
import java.util.List;
import utilities.communication.SerializableBufferedImage;

/**
 *
 * @author Skarab
 */
public class SerializableModel implements Serializable {

    private SerializableBufferedImage map;
    private List<TestBody> bodies;

    public SerializableModel(SerializableBufferedImage map, List<TestBody> bodies) {
        this.map = map;
        this.bodies = bodies;
    }

    public SerializableBufferedImage getMap() {
        return map;
    }

    public List<TestBody> getBodies() {
        return bodies;
    }
}

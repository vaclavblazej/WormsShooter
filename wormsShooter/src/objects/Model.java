package objects;

import java.awt.image.BufferedImage;
import java.util.List;
import utilities.communication.SerializableBufferedImage;

/**
 *
 * @author Skarab
 */
public class Model {

    private BufferedImage map;
    private List<TestBody> bodies;

    public Model(BufferedImage map, List<TestBody> bodies) {
        this.map = map;
        this.bodies = bodies;
    }

    public SerializableModel serialize() {
        return new SerializableModel(new SerializableBufferedImage(map), bodies);
    }

    public void deserialize(SerializableModel sModel) {
        bodies = sModel.getBodies();
        map = sModel.getMap().getImage();
    }
}

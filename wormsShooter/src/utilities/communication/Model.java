package utilities.communication;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;
import objects.TestBody;

/**
 *
 * @author Skarab
 */
public class Model {

    private BufferedImage map;
    private Map<Integer, TestBody> controls;
    private int counter;

    public Model(BufferedImage map, Map<Integer, TestBody> controls, int counter) {
        this.map = map;
        this.controls = controls;
        this.counter = counter;
    }

    public BufferedImage getMap() {
        return map;
    }

    public Map<Integer, TestBody> getControls() {
        return controls;
    }

    public int getCounter() {
        return counter;
    }

    public SerializableModel serialize() {
        Map<Integer, SerializableBody> arr = new HashMap<>();
        for (Integer i : controls.keySet()) {
            arr.put(i, controls.get(i).serialize());
        }
        return new SerializableModel(new SerializableBufferedImage(map), arr, counter);
    }
}

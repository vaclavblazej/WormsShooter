package utilities.communication;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import objects.TestBody;
import utilities.MapInterface;

/**
 *
 * @author Skarab
 */
public class SerializableModel implements Serializable {

    private SerializableBufferedImage map;
    private Map<Integer, SerializableBody> controls;
    private int counter;

    public SerializableModel(SerializableBufferedImage map, Map<Integer, SerializableBody> controls, int counter) {
        this.map = map;
        this.controls = controls;
        this.counter = counter;
    }

    public Model deserialize(MapInterface listener) {
        Map<Integer, TestBody> arr = new HashMap<>();
        for (Integer i : controls.keySet()) {
            arr.put(i, controls.get(i).deserialize(listener));
        }
        return new Model(map.getImage(), arr, counter);
    }
}

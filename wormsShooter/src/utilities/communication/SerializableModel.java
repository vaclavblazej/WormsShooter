package utilities.communication;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import objects.Miscellaneous;
import objects.TestBody;
import objects.items.ItemFactory;
import utilities.AbstractView;

/**
 *
 * @author Skarab
 */
public class SerializableModel implements Serializable {

    private SerializableBufferedImage map;
    private Map<Integer, SerializableBody> controls;
    private List<Miscellaneous> objects;
    private ItemFactory factory;
    private int counter;

    public SerializableModel(
            SerializableBufferedImage map,
            ItemFactory factory,
            List<Miscellaneous> objects,
            Map<Integer, SerializableBody> controls,
            int counter) {
        this.map = map;
        this.controls = controls;
        this.objects = objects;
        this.factory = factory;
        this.counter = counter;
    }

    public Model deserialize(AbstractView listener) {
        Map<Integer, TestBody> arr = new HashMap<>();
        for (Integer i : controls.keySet()) {
            arr.put(i, controls.get(i).deserialize(listener));
        }
        return new Model(map.getImage(), arr, objects, factory, counter);
    }
}

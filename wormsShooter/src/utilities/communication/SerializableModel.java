package utilities.communication;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import objects.Miscellaneous;
import objects.Body;
import objects.items.ItemFactory;
import utilities.AbstractView;

/**
 *
 * @author Skarab
 */
public class SerializableModel implements Serializable {

    private SerializableMapClass map;
    private Map<Integer, SerializableBody> controls;
    private List<Miscellaneous> objects;
    private ItemFactory factory;
    private int counter;

    public SerializableModel(
            SerializableMapClass map,
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

    SerializableModel(SerializableBufferedImage serialize, ItemFactory factory, List<Miscellaneous> objects, Map<Integer, SerializableBody> arr, int counter) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public Model deserialize(AbstractView listener) {
        Map<Integer, Body> arr = new HashMap<>();
        for (Integer i : controls.keySet()) {
            arr.put(i, controls.get(i).deserialize(listener));
        }
        return new Model(map.deserialize(), arr, objects, factory, counter);
    }
}

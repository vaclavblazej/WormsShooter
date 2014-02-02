package utilities.communication;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import objects.Miscellaneous;
import objects.TestBody;
import objects.items.ItemFactory;
import utilities.MapClass;

/**
 *
 * @author Skarab
 */
public class Model {

    private MapClass map;
    private Map<Integer, TestBody> controls;
    private List<Miscellaneous> objects;
    private ItemFactory factory;
    private int counter;

    public Model(
            MapClass map,
            Map<Integer, TestBody> controls,
            List<Miscellaneous> objects,
            ItemFactory factory,
            int counter) {
        this.map = map;
        this.controls = controls;
        this.objects = objects;
        this.factory = factory;
        this.counter = counter;
    }

    public MapClass getMap() {
        return map;
    }

    public Map<Integer, TestBody> getControls() {
        return controls;
    }

    public List<Miscellaneous> getObjects() {
        return objects;
    }

    public ItemFactory getFactory() {
        return factory;
    }

    public int getCounter() {
        return counter;
    }

    public SerializableModel serialize() {
        Map<Integer, SerializableBody> arr = new HashMap<>();
        for (Integer i : controls.keySet()) {
            arr.put(i, controls.get(i).serialize());
        }
        return new SerializableModel(map.serialize(), factory, objects, arr, counter);
    }
}

package utilities.communication;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import objects.Body;
import objects.GraphicComponent;
import objects.items.ItemFactory;
import utilities.AbstractView;

/**
 *
 * @author Skarab
 */
public class SerializableModel implements Serializable {

    private SerializableMapClass map;
    private Map<Integer, SerializableBody> controls;
    private List<GraphicComponent> objects;
    private ItemFactory factory;

    public SerializableModel(
            SerializableMapClass map,
            ItemFactory factory,
            List<GraphicComponent> objects,
            Map<Integer, SerializableBody> controls) {
        this.map = map;
        this.controls = controls;
        this.objects = objects;
        this.factory = factory;
    }

    public Model deserialize(AbstractView view) {
        Map<Integer, Body> arr = new HashMap<>();
        for (Integer i : controls.keySet()) {
            arr.put(i, controls.get(i).deserialize(view));
        }
        return new Model(map.deserialize(view), arr, objects, factory);
    }
}

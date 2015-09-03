package utilities.communication;

import objects.Body;
import objects.GraphicComponent;
import objects.items.ItemFactory;
import utilities.MapClass;
import utilities.SendableVia;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author V�clav Bla�ej
 */
public class Model implements SendableVia<Model, SerializableModel> {

    private MapClass map;
    private Map<Integer, Body> controls;
    private List<GraphicComponent> objects;
    private ItemFactory factory;

    public Model(
            MapClass map,
            Map<Integer, Body> controls,
            List<GraphicComponent> objects,
            ItemFactory factory) {
        this.map = map;
        this.controls = controls;
        this.objects = objects;
        this.factory = factory;
    }

    public MapClass getMap() {
        return map;
    }

    public Map<Integer, Body> getControls() {
        return controls;
    }

    public List<GraphicComponent> getObjects() {
        return objects;
    }

    public ItemFactory getFactory() {
        return factory;
    }

    @Override
    public SerializableModel serialize() {
        Map<Integer, SerializableBody> arr = new HashMap<>();
        for (Integer i : controls.keySet()) {
            arr.put(i, controls.get(i).serialize());
        }
        return new SerializableModel(map.serialize(), factory, objects, arr);
    }
}

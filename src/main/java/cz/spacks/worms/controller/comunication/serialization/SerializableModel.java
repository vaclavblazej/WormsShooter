package cz.spacks.worms.controller.comunication.serialization;

import cz.spacks.worms.model.objects.Body;
import cz.spacks.worms.model.objects.GraphicComponent;
import cz.spacks.worms.model.objects.Model;
import cz.spacks.worms.model.objects.items.ItemFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 */
public class SerializableModel implements SeriaziblePair<Model, SerializableModel> {

    private SerializableMapModel map;
    private Map<Integer, SerializableBody> controls;
    private List<GraphicComponent> objects;
    private ItemFactory factory;

    @Override
    public SerializableModel serialize(Model model) {
        map = new SerializableMapModel().serialize(model.getMap());
        factory = model.getFactory();
        objects = model.getObjects();
        controls = new HashMap<>();
        model.getControls().forEach((integer, body) -> controls.put(integer, new SerializableBody().serialize(body)));
        return this;
    }

    @Override
    public Model deserialize() {
        Map<Integer, Body> arr = new HashMap<>();
        for (Integer i : controls.keySet()) {
            arr.put(i, controls.get(i).deserialize());
        }
        return new Model(map.deserialize(), arr, objects, factory);
    }
}

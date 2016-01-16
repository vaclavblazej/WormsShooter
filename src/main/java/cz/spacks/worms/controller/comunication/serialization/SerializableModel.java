package cz.spacks.worms.controller.comunication.serialization;

import cz.spacks.worms.model.objects.Body;
import cz.spacks.worms.model.objects.GraphicComponent;
import cz.spacks.worms.model.objects.WorldModel;
import cz.spacks.worms.model.objects.items.ItemFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 */
public class SerializableModel implements SeriaziblePair<WorldModel, SerializableModel> {

    private SerializableMapModel map;
    private Map<Integer, SerializableBody> controls;
    private ItemFactory factory;

    @Override
    public SerializableModel serialize(WorldModel worldModel) {
        map = new SerializableMapModel().serialize(worldModel.getMap());
        factory = worldModel.getFactory();
        controls = new HashMap<>();
        worldModel.getControls().forEach((integer, body) -> controls.put(integer, new SerializableBody().serialize(body)));
        return this;
    }

    @Override
    public WorldModel deserialize() {
        Map<Integer, Body> arr = new HashMap<>();
        for (Integer i : controls.keySet()) {
            arr.put(i, controls.get(i).deserialize());
        }
        return new WorldModel(map.deserialize(), arr, factory);
    }
}

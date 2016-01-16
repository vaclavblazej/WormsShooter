package cz.spacks.worms.model.objects;

import cz.spacks.worms.model.MapModel;
import cz.spacks.worms.model.objects.items.ItemFactory;

import java.util.Map;

/**
 *
 */
public class WorldModel {

    private MapModel map;
    private Map<Integer, Body> controls;
    private ItemFactory factory;

    public WorldModel(
            MapModel map,
            Map<Integer, Body> controls,
            ItemFactory factory) {
        this.map = map;
        this.controls = controls;
        this.factory = factory;
    }

    public MapModel getMap() {
        return map;
    }

    public Map<Integer, Body> getControls() {
        return controls;
    }

    public ItemFactory getFactory() {
        return factory;
    }
}

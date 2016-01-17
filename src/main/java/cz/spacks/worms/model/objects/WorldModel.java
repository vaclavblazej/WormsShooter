package cz.spacks.worms.model.objects;

import cz.spacks.worms.model.MapModel;
import cz.spacks.worms.model.objects.items.ItemFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 */
public class WorldModel {

    private MapModel map;
    private Map<Integer, Body> controls;
    private ItemFactory factory;
    protected List<Body> bodies;

    public WorldModel(
            MapModel map,
            Map<Integer, Body> controls,
            ItemFactory factory) {
        this.map = map;
        this.controls = controls;
        this.factory = factory;
        this.bodies = new ArrayList<>();
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

    public List<Body> getBodies() {
        return bodies;
    }

    public void setBodies(List<Body> bodies) {
        this.bodies = bodies;
    }
}

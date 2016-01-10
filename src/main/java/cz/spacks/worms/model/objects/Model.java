package cz.spacks.worms.model.objects;

import cz.spacks.worms.model.MapModel;
import cz.spacks.worms.model.objects.items.ItemFactory;

import java.util.List;
import java.util.Map;

/**
 *
 */
public class Model {

    private MapModel map;
    private Map<Integer, Body> controls;
    private List<GraphicComponent> objects;
    private ItemFactory factory;

    public Model(
            MapModel map,
            Map<Integer, Body> controls,
            List<GraphicComponent> objects,
            ItemFactory factory) {
        this.map = map;
        this.controls = controls;
        this.objects = objects;
        this.factory = factory;
    }

    public MapModel getMap() {
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
}

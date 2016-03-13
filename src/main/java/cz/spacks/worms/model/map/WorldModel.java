package cz.spacks.worms.model.map;

import cz.spacks.worms.model.materials.MaterialModel;
import cz.spacks.worms.model.objects.Body;
import cz.spacks.worms.model.objects.items.DroppedItem;
import cz.spacks.worms.model.objects.items.Item;
import cz.spacks.worms.model.objects.items.ItemFactory;

import java.awt.*;
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
    private List<WorldEvent> continuingEvents;
    private List<WorldEvent> todoEvents;
    private List<DroppedItem> droppedItems;

    public WorldModel(
            MapModel map,
            Map<Integer, Body> controls,
            ItemFactory factory) {
        this.map = map;
        this.controls = controls;
        this.factory = factory;
        this.continuingEvents = new ArrayList<>();
        this.todoEvents = new ArrayList<>();
        this.droppedItems = new ArrayList<>();
    }

    public void addRigidItem(Item item, Point position) {
        droppedItems.add(new DroppedItem(item, position));
    }

    public List<DroppedItem> getDroppedItems() {
        return droppedItems;
    }

    public void addEvent(WorldEvent worldEvent) {
        todoEvents.add(worldEvent);
    }

    public void addContinuingEvent(WorldEvent worldEvent) {
        continuingEvents.add(worldEvent);
    }

    public List<WorldEvent> getContinuingEvents() {
        return continuingEvents;
    }

    public List<WorldEvent> getTodoEvents() {
        return todoEvents;
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

    public void setMaterialModelCache(MaterialModel materialModelCache) {
        map.setMaterialModelCache(materialModelCache);
    }
}

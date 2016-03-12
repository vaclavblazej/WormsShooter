package cz.spacks.worms.model.map;

import cz.spacks.worms.controller.events.WorldEvent;
import cz.spacks.worms.model.objects.Body;
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
    private List<WorldEvent> continuingEvents;
    private List<WorldEvent> todoEvents;

    public WorldModel(
            MapModel map,
            Map<Integer, Body> controls,
            ItemFactory factory) {
        this.map = map;
        this.controls = controls;
        this.factory = factory;
        this.continuingEvents = new ArrayList<>();
        this.todoEvents = new ArrayList<>();
    }

    public void addEvent(WorldEvent worldEvent){
        todoEvents.add(worldEvent);
    }

    public void addContinuingEvent(WorldEvent worldEvent){
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
}

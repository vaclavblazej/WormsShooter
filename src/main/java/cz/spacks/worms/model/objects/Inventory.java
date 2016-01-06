package cz.spacks.worms.model.objects;


import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class Inventory { // todo consider extends ArrayList<ItemsCount>

    private List<ItemsCount> components;

    public Inventory() {
        components = new ArrayList<>();
    }

    public void add(List<ItemsCount> addedComponents) {
        components.addAll(addedComponents);
    }

    public void remove(List<ItemsCount> addedComponents) {
        components.removeAll(addedComponents);
        // todo notify listeners
    }

    public void setComponents(List<ItemsCount> components) {
        this.components = components;
    }

    public List<ItemsCount> getComponents() {
        return components;
    }

    public boolean contains(List<ItemsCount> ingredientsModel) {
        return components.containsAll(ingredientsModel);
    }
}

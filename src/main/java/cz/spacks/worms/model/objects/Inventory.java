package cz.spacks.worms.model.objects;


import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class Inventory extends ArrayList<ItemsCount>{

    private ArrayList<InventoryListener> inventoryListeners;

    public Inventory() {
        inventoryListeners = new ArrayList<>();
    }

    public void addListener(InventoryListener listener){
        inventoryListeners.add(listener);
    }

    public void removeListener(InventoryListener listener){
        inventoryListeners.remove(listener);
    }

    public void add(List<ItemsCount> addedComponents) {
        addAll(addedComponents);
        change();
    }

    public void remove(List<ItemsCount> addedComponents) {
        removeAll(addedComponents);
        change();
    }

    private void change(){
        for (InventoryListener listener : inventoryListeners) {
            listener.change();
        }
    }

    public List<ItemsCount> getComponents() {
        return this;
    }

    public boolean contains(List<ItemsCount> ingredientsModel) {
        return containsAll(ingredientsModel);
    }
}

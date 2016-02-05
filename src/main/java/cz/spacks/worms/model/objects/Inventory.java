package cz.spacks.worms.model.objects;


import java.util.ArrayList;
import java.util.Collection;

/**
 *
 */
public class Inventory extends ArrayList<ItemsCount>{

    private ArrayList<InventoryListener> listeners;

    public Inventory() {
        this.listeners = new ArrayList<>();
    }

    public void addListener(InventoryListener listener){
        listeners.add(listener);
    }

    public void dataChanged(){
        for (InventoryListener listener : listeners) {
            listener.dataChanged();
        }
    }

    @Override
    public ItemsCount remove(int index) {
        final ItemsCount remove = super.remove(index);
        dataChanged();
        return remove;
    }

    @Override
    public boolean add(ItemsCount itemsCount) {
        final boolean add = super.add(itemsCount);
        dataChanged();
        return add;
    }

    @Override
    public boolean addAll(Collection<? extends ItemsCount> c) {
        final boolean b = super.addAll(c);
        dataChanged();
        return b;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        final boolean b = super.removeAll(c);
        dataChanged();
        return b;
    }
}

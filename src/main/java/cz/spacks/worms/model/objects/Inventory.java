package cz.spacks.worms.model.objects;


import cz.spacks.worms.model.objects.items.Item;
import cz.spacks.worms.model.objects.items.ItemBlueprint;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 *
 */
public class Inventory extends ArrayList<ItemsCount> {

    private ArrayList<InventoryListener> listeners;

    public Inventory() {
        this.listeners = new ArrayList<>();
    }

    public void addListener(InventoryListener listener) {
        listeners.add(listener);
    }

    public void dataChanged() {
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
        this.addOne(itemsCount);
        dataChanged();
        return true;
    }

    public boolean add(Item itemsCount) {
        this.addOne(new ItemsCount(itemsCount, 1));
        dataChanged();
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends ItemsCount> c) {
        for (ItemsCount itemsCount : c) {
            this.addOne(itemsCount);
        }
        dataChanged();
        return true;
    }

    private void addOne(ItemsCount itemsCount) {
        final ItemsCount found = find(itemsCount.itemBlueprint);
        if (found != null) {
            found.count += itemsCount.count;
        } else {
            super.add(itemsCount);
        }
    }

    public ItemsCount find(ItemBlueprint blueprint) {
        final Iterator<ItemsCount> iterator = this.iterator();
        ItemsCount found = null;
        while (iterator.hasNext()) {
            final ItemsCount next = iterator.next();
            if (next.itemBlueprint.equals(blueprint)) {
                found = next;
                break;
            }
        }
        return found;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        final boolean b = super.removeAll(c);
        dataChanged();
        return b;
    }
}

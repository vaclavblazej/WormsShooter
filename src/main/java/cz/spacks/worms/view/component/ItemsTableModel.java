package cz.spacks.worms.view.component;

import cz.spacks.worms.model.objects.Inventory;
import cz.spacks.worms.model.objects.InventoryListener;
import cz.spacks.worms.model.objects.ItemsCount;
import cz.spacks.worms.view.ComponentTableModel;

/**
 *
 */
public class ItemsTableModel extends ComponentTableModel<ItemsCount> implements InventoryListener{

    public ItemsTableModel() {
        this(new Inventory());
    }

    public ItemsTableModel(Inventory inv) {
        super("Items", "Count");
        setInventory(inv);
    }

    public ItemsTableModel(String... columnNames) {
        super(columnNames);
        setInventory(new Inventory());
    }

    public void setInventory(Inventory inventory) {
        inventory.addListener(this);
        this.setComponents(inventory);
    }

    @Override
    public Object getElementValueAt(ItemsCount itemsCount, int idx) {
        switch (idx){
            case 0:
                return itemsCount.itemBlueprint.getName();
            case 1:
                return itemsCount.count;
        }
        return null;
    }

    @Override
    public void dataChanged() {
        fireTableDataChanged();
        System.out.println("data changed");
    }
}

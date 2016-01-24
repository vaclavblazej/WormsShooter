package cz.spacks.worms.view.component;

import cz.spacks.worms.model.objects.ItemsCount;
import cz.spacks.worms.view.ComponentTableModel;

import java.util.List;

/**
 *
 */
public class ItemsTableModel extends ComponentTableModel {

    public ItemsTableModel(List<ItemsCount> items) {
        super("", "");
        for (ItemsCount item : items) {
            this.add(item.itemBlueprint, 1);
        }
    }
}

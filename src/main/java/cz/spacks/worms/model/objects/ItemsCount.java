package cz.spacks.worms.model.objects;

import cz.spacks.worms.model.objects.items.ItemBlueprint;

import java.util.ArrayList;
import java.util.List;


public class ItemsCount {
    public ItemBlueprint itemBlueprint;
    public int count;

    public ItemsCount(ItemBlueprint itemBlueprint, int count) {
        this.itemBlueprint = itemBlueprint;
        this.count = count;
    }

    public static class Builder {
        private List<ItemsCount> itemsCounts;

        public Builder() {
            itemsCounts = new ArrayList<>();
        }

        public Builder add(ItemBlueprint itemBlueprint, int count) {
            itemsCounts.add(new ItemsCount(itemBlueprint, count));
            return this;
        }

        public List<ItemsCount> build() {
            return itemsCounts;
        }
    }
}
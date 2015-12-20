package cz.spacks.worms.objects.items;

import java.io.Serializable;
import java.util.EnumMap;
import java.util.Map;

/**
 *
 */
public class ItemFactory implements Serializable {

    private Map<ItemEnum, ItemBlueprint> items;
    private Crafting recipes;

    public ItemFactory() {
        items = new EnumMap<>(ItemEnum.class);
        recipes = new Crafting();
    }

    public void addItem(ItemEnum en, ItemBlueprint blueprint) {
        items.put(en, blueprint);
    }

    public Item get(ItemEnum en) {
        return items.get(en).getInstance();
    }

    public Crafting getRecipes() {
        return recipes;
    }

    public ItemBlueprint getBlueprint(ItemEnum en) {
        return items.get(en);
    }
}

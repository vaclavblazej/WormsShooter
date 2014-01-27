package objects.items;

import java.io.Serializable;
import java.util.EnumMap;
import java.util.Map;

/**
 *
 * @author Skarab
 */
public class ItemFactory implements Serializable {

    private Map<ItemEnum, ItemBlueprint> items;

    public ItemFactory() {
        items = new EnumMap<>(ItemEnum.class);
    }

    public void addItem(ItemEnum en, ItemBlueprint blueprint) {
        items.put(en, blueprint);
    }

    public Item get(ItemEnum en) {
        return items.get(en).getInstance();
    }
}

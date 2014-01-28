package objects.items;

import java.awt.Point;
import java.io.Serializable;

/**
 *
 * @author Skarab
 */
public class ItemBlueprint implements Serializable, Comparable<ItemBlueprint> {

    private String name;
    private Point size;

    public ItemBlueprint(String name, int x, int y) {
        this(name, new Point(x, y));
    }

    public ItemBlueprint(String name, Point size) {
        this.name = name;
        this.size = size;
    }

    public Item getInstance() {
        return new Item(name, size);
    }

    public String getName() {
        return name;
    }

    @Override
    public int compareTo(ItemBlueprint o) {
        return name.compareTo(o.getName());
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof ItemBlueprint)) {
            return false;
        }
        return name.equals(((ItemBlueprint) obj).name);
    }
}

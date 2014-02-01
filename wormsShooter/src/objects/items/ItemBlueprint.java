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
    private boolean usable;

    public ItemBlueprint(String name, boolean usable, int x, int y) {
        this(name, usable, new Point(x, y));
    }

    public ItemBlueprint(String name, boolean usable, Point size) {
        this.name = name;
        this.usable = usable;
        this.size = size;
    }

    public Item getInstance() {
        return new Item(name, usable, size);
    }

    public String getName() {
        return name;
    }

    boolean isUsable() {
        return usable;
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

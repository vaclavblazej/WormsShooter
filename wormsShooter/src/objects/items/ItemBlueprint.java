package objects.items;

import java.awt.Point;
import java.io.Serializable;

/**
 *
 * @author Skarab
 */
public class ItemBlueprint implements Serializable {

    private String name;
    private Point size;

    public ItemBlueprint(String name, int x, int y) {
        this.name = name;
        this.size = new Point(x, y);
    }

    public Item getInstance() {
        return new Item(name, size);
    }
}

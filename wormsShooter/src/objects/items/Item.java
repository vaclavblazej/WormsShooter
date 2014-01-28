package objects.items;

import java.awt.Point;
import java.io.Serializable;

/**
 *
 * @author Skarab
 */
public class Item extends ItemBlueprint implements Serializable {

    public Item(String name, Point size) {
        super(name, size);
    }

    @Override
    public Item getInstance() {
        return this;
    }
}

package objects.items;

import java.awt.Point;
import java.io.Serializable;

/**
 *
 * @author Skarab
 */
public class Item extends ItemBlueprint implements Serializable {

    public Item(String name, boolean usable, Point size) {
        super(name, usable, size);
    }

    @Override
    public Item getInstance() {
        return this;
    }
}

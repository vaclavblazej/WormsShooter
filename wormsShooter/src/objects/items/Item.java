package objects.items;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.Serializable;

/**
 *
 * @author Skarab
 */
public class Item extends ItemBlueprint implements Serializable {

    public Item(String name, boolean usable, Point size, BufferedImage img) {
        super(name, usable, size, img);
    }

    @Override
    public Item getInstance() {
        return this;
    }
}

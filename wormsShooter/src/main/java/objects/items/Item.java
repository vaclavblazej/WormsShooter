package objects.items;

import objects.items.itemActions.ItemAction;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.Serializable;

/**
 * @author Skarab
 */
public class Item extends ItemBlueprint implements Serializable {

    public Item(String name, boolean usable, Point p, ItemCategory category,
                BufferedImage img, ItemAction action) {
        super(name, usable, p, category, img, action);
    }

    @Override
    public Item getInstance() {
        return this;
    }
}

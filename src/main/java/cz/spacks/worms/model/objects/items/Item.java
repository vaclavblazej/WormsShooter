package cz.spacks.worms.model.objects.items;

import cz.spacks.worms.controller.events.itemActions.ItemAction;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.Serializable;

/**
 *
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

package cz.spacks.worms.model.objects.items;

import java.awt.*;

/**
 *
 */
public class DroppedItem {

    private Item item;
    private Point position;

    public DroppedItem(Item item, Point position) {
        this.item = item;
        this.position = position;
    }

    public Item getItem() {
        return item;
    }

    public Point getPosition() {
        return position;
    }
}

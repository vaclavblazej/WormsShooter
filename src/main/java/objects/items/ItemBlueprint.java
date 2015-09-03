package objects.items;

import objects.items.itemActions.ItemAction;
import utilities.communication.SerializableBufferedImage;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.Serializable;

/**
 * @author Václav Blažej
 */
public class ItemBlueprint implements Serializable, Comparable<ItemBlueprint> {

    private String name;
    private Point size;
    private boolean usable;
    private ItemAction action;
    private SerializableBufferedImage image;
    private ItemCategory category;

    public ItemBlueprint(String name, boolean usable, int x, int y,
                         ItemCategory category, BufferedImage img, ItemAction action) {
        this(name, usable, new Point(x, y), category, img, action);
    }

    public ItemBlueprint(String name, boolean usable, Point p,
                         ItemCategory category, BufferedImage img, ItemAction action) {
        this.name = name;
        this.usable = usable;
        this.size = p;
        this.category = category;
        this.image = new SerializableBufferedImage(img);
        this.action = action;
    }

    public ItemCategory getCategory() {
        return category;
    }

    public ItemAction getAction() {
        return action;
    }

    public Item getInstance() {
        return new Item(name, usable, size, category, image.deserialize(), action);
    }

    public BufferedImage getImage() {
        return image.deserialize();
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

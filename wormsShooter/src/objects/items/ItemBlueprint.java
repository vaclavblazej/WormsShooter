package objects.items;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.Serializable;
import objects.items.itemActions.ItemAction;
import utilities.communication.SerializableBufferedImage;

/**
 *
 * @author Skarab
 */
public class ItemBlueprint implements Serializable, Comparable<ItemBlueprint> {

    private String name;
    private Point size;
    private boolean usable;
    private ItemAction action;
    private SerializableBufferedImage image;
    private transient BufferedImage cache = null;
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
        this.cache = img;
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
        cacheImg();
        return new Item(name, usable, size, category, cache, action);
    }

    private void cacheImg() {
        if (cache == null) {
            cache = image.getImage();
        }
    }

    public BufferedImage getImage() {
        cacheImg();
        return cache;
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

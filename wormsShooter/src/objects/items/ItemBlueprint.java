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

    public ItemBlueprint(String name, boolean usable, int x, int y, BufferedImage img, ItemAction action) {
        this(name, usable, new Point(x, y), img, action);
    }

    public ItemBlueprint(String name, boolean usable, Point p, BufferedImage img, ItemAction action) {
        this.name = name;
        this.usable = usable;
        this.size = p;
        this.image = new SerializableBufferedImage(img);
        this.action = action;
    }

    public ItemAction getAction() {
        return action;
    }

    public Item getInstance() {
        return new Item(name, usable, size, image.getImage(), action);
    }

    public BufferedImage getImage() {
        return image.getImage();
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

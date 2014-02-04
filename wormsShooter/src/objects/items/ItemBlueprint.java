package objects.items;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.Serializable;
import objects.items.itemActions.ItemAction;
import objects.items.itemActions.ItemActionShoot;
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
    private BufferedImage image;

    public ItemBlueprint(String name, boolean usable, int x, int y, SerializableBufferedImage img) {
        this(name, usable, new Point(x, y), img);
    }

    public ItemBlueprint(String name, boolean usable, Point size, SerializableBufferedImage img) {
        this.name = name;
        this.usable = usable;
        this.size = size;
        this.image = img.getImage();
        action = new ItemActionShoot();
    }

    public ItemAction getAction() {
        return action;
    }

    public Item getInstance() {
        return new Item(name, usable, size, image);
    }
    
    public BufferedImage getImage() {
        return image;
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

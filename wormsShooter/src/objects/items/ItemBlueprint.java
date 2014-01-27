package objects.items;

import java.awt.Point;
import java.awt.image.BufferedImage;

/**
 *
 * @author Skarab
 */
public class ItemBlueprint {

    private BufferedImage image;
    private String name;
    private Point size;

    public ItemBlueprint(BufferedImage image, String name, int x, int y) {
        this.image = image;
        this.name = name;
        this.size = new Point(x, y);
    }

    public Item getInstance() {
        return new Item(name, image, size);
    }
}

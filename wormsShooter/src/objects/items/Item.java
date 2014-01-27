package objects.items;

import java.awt.Point;
import java.awt.image.BufferedImage;

/**
 *
 * @author Skarab
 */
public class Item {

    private BufferedImage image;
    private String name;
    private Point size;

    public Item(String name, BufferedImage image, Point size) {
        this.name = name;
        this.image = image;
        this.size = size;
    }

    Object get(int columnIndex) {
        switch (columnIndex) {
            case 0:
                return name;
        }
        return null;
    }
}

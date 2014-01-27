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
    private int count;
    private Point size;

    public Item(String name, BufferedImage image, int x, int y) {
        this.name = name;
        this.count = 0;
        this.image = image;
        this.size = new Point(x, y);
    }

    Object get(int columnIndex) {
        switch(columnIndex){
            case 0:
                return name;
            case 1:
                return count;
            case 2:
                return size;
        }
        return null;
    }
}

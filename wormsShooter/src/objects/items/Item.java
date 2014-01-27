package objects.items;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.Serializable;

/**
 *
 * @author Skarab
 */
public class Item implements Serializable{

    private String name;
    private Point size;

    public Item(String name, Point size) {
        this.name = name;
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

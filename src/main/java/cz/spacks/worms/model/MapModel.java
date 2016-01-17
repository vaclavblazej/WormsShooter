package cz.spacks.worms.model;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.logging.Logger;

/**
 *
 */
public class MapModel {

    private static final Logger logger = Logger.getLogger(MapModel.class.getName());

    private BufferedImage map;

    public MapModel(BufferedImage map) {
        this.map = map;
    }

    public int getWidth() {
        return map.getWidth();
    }

    public int getHeight() {
        return map.getHeight();
    }

    public Graphics getGraphics() {
        return map.getGraphics();
    }

    public Integer getRGB(int x, int y) {
        try {
            return map.getRGB(x, y);
        } catch (ArrayIndexOutOfBoundsException ex) {
            return 0;
        }
    }

    public MapModel getSubmap(Point offset, Dimension size) throws ArrayIndexOutOfBoundsException {
        final int width = map.getWidth();
        final int height = map.getHeight();
        if (offset.x < 0)
            throw new ArrayIndexOutOfBoundsException("You tried to get subimage with left x index = " + offset.x);
        if (offset.y < 0)
            throw new ArrayIndexOutOfBoundsException("You tried to get subimage with top y index = " + offset.y);
        if (offset.x + size.width > width)
            throw new ArrayIndexOutOfBoundsException("You tried to get subimage with right x index = " + (offset.x + size.width));
        if (offset.y + size.height > height)
            throw new ArrayIndexOutOfBoundsException("You tried to get subimage with bottom y index = " + (offset.y + size.height));
        final BufferedImage subimage = map.getSubimage(offset.x, offset.y, size.width, size.height);
        return new MapModel(subimage);
    }

    public BufferedImage getImage() {
        return map;
    }
}

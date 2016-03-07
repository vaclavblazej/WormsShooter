package cz.spacks.worms.view;

import cz.spacks.worms.model.map.MapModel;
import cz.spacks.worms.model.map.MapModelListener;
import cz.spacks.worms.model.map.Material;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 *
 */
public class MapViewModel implements MapModelListener {

    private BufferedImage mapImageCache;
    private MapModel mapModel;

    public MapViewModel() {
//        final Dimension dimensions = mapModel.getDimensions();
//        BufferedImage mapImageCache = new BufferedImage(dimensions.width, dimensions.height, BufferedImage.TYPE_4BYTE_ABGR);
//        SpriteLoader.loadSprite("Map");
//        SpriteLoader.set(150, 100);
//        this.mapImageCache = toCompatibleImage(SpriteLoader.getSprite().getFrame());
        this.mapImageCache = new BufferedImage(100, 100, BufferedImage.TYPE_4BYTE_ABGR);
    }

    public MapViewModel(BufferedImage bufferedImage) {
        this.mapImageCache = bufferedImage;
    }

    public int getWidth() {
        return mapImageCache.getWidth();
    }

    public int getHeight() {
        return mapImageCache.getHeight();
    }

    public Graphics getGraphics() {
        return mapImageCache.getGraphics();
    }

    public Integer getRGB(int x, int y) {
        try {
            return mapImageCache.getRGB(x, y);
        } catch (ArrayIndexOutOfBoundsException ex) {
            return 0;
        }
    }

    public BufferedImage getSubimage(Point offset, Dimension size) throws ArrayIndexOutOfBoundsException {
        final int width = mapImageCache.getWidth();
        final int height = mapImageCache.getHeight();
        if (offset.x < 0)
            throw new ArrayIndexOutOfBoundsException("You tried to get subimage with left x index = " + offset.x);
        if (offset.y < 0)
            throw new ArrayIndexOutOfBoundsException("You tried to get subimage with top y index = " + offset.y);
        if (offset.x + size.width > width)
            throw new ArrayIndexOutOfBoundsException("You tried to get subimage with right x index = " + (offset.x + size.width));
        if (offset.y + size.height > height)
            throw new ArrayIndexOutOfBoundsException("You tried to get subimage with bottom y index = " + (offset.y + size.height));
        return mapImageCache.getSubimage(offset.x, offset.y, size.width, size.height);
    }

    public BufferedImage getImage() {
        return mapImageCache;
    }

    /**
     * http://stackoverflow.com/questions/196890/java2d-performance-issues
     */
    private BufferedImage toCompatibleImage(BufferedImage image) {
        // obtain the current system graphical settings
        GraphicsConfiguration gfx_config = GraphicsEnvironment.
                getLocalGraphicsEnvironment().getDefaultScreenDevice().
                getDefaultConfiguration();

        // if image is already compatible and optimized for current system settings, simply return it
        if (image.getColorModel().equals(gfx_config.getColorModel()))
            return image;

        // image is not optimized, so create a new image that is
        BufferedImage new_image = gfx_config.createCompatibleImage(
                image.getWidth(), image.getHeight(), image.getTransparency());

        // get the graphics context of the new image to draw the old image on
        Graphics2D g2d = (Graphics2D) new_image.getGraphics();

        // actually draw the image and dispose of context no longer needed
        g2d.drawImage(image, 0, 0, null);
        g2d.dispose();

        // return the new optimized image
        return new_image;
    }

    @Override
    public void chunkUpdated(Point position) {
        final Material material = mapModel.getChunk(position).getMaterials().get(0).getMaterial();
        mapImageCache.setRGB(position.x, position.y, material.color.getRGB());
    }

    @Override
    public void areaUpdated(Point start, Point end) {
        for (int y = start.y; y < end.y; y++) {
            for (int x = start.x; x < end.x; x++) {
                chunkUpdated(new Point(x, y));
            }
        }
    }

    @Override
    public void setModel(MapModel mapModel) {
        this.mapModel = mapModel;
        final Dimension dimensions = mapModel.getDimensions();
        mapImageCache = new BufferedImage(dimensions.width, dimensions.height, BufferedImage.TYPE_4BYTE_ABGR);
        areaUpdated(new Point(0, 0), new Point(dimensions.width, dimensions.height));
    }
}

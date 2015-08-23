package utilities.materials;

import client.ClientView;
import utilities.MapClass;
import utilities.spritesheets.SpriteLoader;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Skarab
 */
public class MaterialVisuals implements Serializable {

    private static Map<Integer, BufferedImage> images;
    private static final int SIZE = 20;

    static {
        images = new HashMap<>();
        SpriteLoader.loadSprite("Materials");
        SpriteLoader.set(20, 20);
//        images.put(Materials.AIR);
//        addMaterial(WATER);
        addImage(MaterialEnum.DIRT, 7, 2);
        addImage(MaterialEnum.GRASS, 0, 3);
        addImage(MaterialEnum.STONE, 4, 2);
        addImage(MaterialEnum.SAND, 8, 2);
    }

    private static void addImage(MaterialEnum mat, int x, int y) {
        images.put(ClientView.getInstance().getMaterial().getColor(mat).getRGB(),
                SpriteLoader.getRawSprite(x, y));
    }

    private static BufferedImage getImage(int rgb) {
        if (images.containsKey(rgb)) {
            return images.get(rgb);
        }
        return null;
    }

    public static void redraw(MapClass source, BufferedImage dest) {
        Graphics g = dest.getGraphics();
        BufferedImage sourceImage = source.getImage();
        g.drawImage(sourceImage, 0, 0, dest.getWidth(), dest.getHeight(), null);
        int w = sourceImage.getWidth();
        int h = sourceImage.getHeight();
        int ratio = dest.getWidth() / w;
        BufferedImage b;
        for (int j = 0; j < h; j++) {
            for (int i = 0; i < w; i++) {
                b = getImage(source.getRGB(i, j));
                if (b != null) {
                    g.drawImage(b, i * ratio, j * ratio, b.getWidth(), b.getHeight(), null);
                }
                g.setColor(new Color(0, 0, 0, source.getShadow(i, j)));
                g.fillRect(i * ratio, j * ratio, SIZE, SIZE);
            }
        }
        g.dispose();
    }

    private MaterialVisuals() {
    }
}

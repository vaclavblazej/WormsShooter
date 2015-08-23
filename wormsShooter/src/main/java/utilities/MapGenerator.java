package utilities;

import utilities.materials.Material;
import utilities.materials.MaterialEnum;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * @author Václav Blažej
 */
public class MapGenerator {

    private static BufferedImage map;
    private static Material material;
    private static Graphics2D g;

    public static BufferedImage generateMap(int width, int height, Material material) {
        MapGenerator.material = material;
        map = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
        g = (Graphics2D) map.getGraphics();
        g.setColor(material.getColor(MaterialEnum.DIRT));
        g.fillRect(0, 0, width, height);
        g.setColor(material.getColor(MaterialEnum.AIR));
        g.fillOval(80, 160, 50, 50);

        changeOnTouch(width, height, g, MaterialEnum.DIRT, MaterialEnum.GRASS, MaterialEnum.AIR);

        g.dispose();
        return map;
    }

    /**
     * Changes material if it is near other material.
     *
     * @param width  scene width
     * @param height scene height
     * @param g      scene graphic
     * @param what   material which should change
     * @param onto   material on which sould be 'what' material changed
     * @param cause  material that causes change of 'what' material if nearby
     */
    public static void changeOnTouch(int width, int height, Graphics2D g,
                                     MaterialEnum what,
                                     MaterialEnum onto,
                                     MaterialEnum cause) {
        for (int i = 1; i < height - 1; i++) {
            for (int j = 1; j < width - 1; j++) {
                /*if (material.getMaterial(getPixel(i, j)) == what) {
                    if (material.getMaterial(getPixel(i+1, j)) == cause ||
                            material.getMaterial(getPixel(i-1, j)) == cause ||
                            material.getMaterial(getPixel(i, j+1)) == cause ||
                            material.getMaterial(getPixel(i, j-1)) == cause) {
                        setPixel(i, j, onto);
                    }
                }*/
            }
        }
    }

    public static Color getPixel(int x, int y) {
        try {
            return new Color(map.getRGB(x, y));
        } catch (ArrayIndexOutOfBoundsException ex) {
            return Color.BLACK;
        }
    }

    public static void setPixel(int x, int y, MaterialEnum mat) {
        g.setColor(material.getColor(mat));
        g.drawLine(x, y, x, y);
    }
}

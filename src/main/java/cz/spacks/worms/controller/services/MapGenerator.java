package cz.spacks.worms.controller.services;

import cz.spacks.worms.model.materials.MaterialEnum;
import cz.spacks.worms.model.materials.MaterialModel;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 *
 */
public class MapGenerator {

    private static BufferedImage map;
    private static MaterialModel materialModel;
    private static Graphics2D g;

    public static BufferedImage generateMap(int width, int height, MaterialModel materialModel) {
        MapGenerator.materialModel = materialModel;
        map = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
        g = (Graphics2D) map.getGraphics();
        g.setColor(materialModel.getColor(MaterialEnum.DIRT));
        g.fillRect(0, 0, width, height);
        g.setColor(materialModel.getColor(MaterialEnum.AIR));
        g.fillOval(80, 160, 50, 50);

        changeOnTouch(width, height, g, MaterialEnum.DIRT, MaterialEnum.GRASS, MaterialEnum.AIR);

        g.dispose();
        return map;
    }

    /**
     * Changes materialModel if it is near other materialModel.
     *
     * @param width  scene width
     * @param height scene height
     * @param g      scene graphic
     * @param what   materialModel which should change
     * @param onto   materialModel on which sould be 'what' materialModel changed
     * @param cause  materialModel that causes change of 'what' materialModel if nearby
     */
    public static void changeOnTouch(int width, int height, Graphics2D g,
                                     MaterialEnum what,
                                     MaterialEnum onto,
                                     MaterialEnum cause) {
        for (int i = 1; i < height - 1; i++) {
            for (int j = 1; j < width - 1; j++) {
                /*if (materialModel.getMaterialModel(getPixel(i, j)) == what) {
                    if (materialModel.getMaterialModel(getPixel(i+1, j)) == cause ||
                            materialModel.getMaterialModel(getPixel(i-1, j)) == cause ||
                            materialModel.getMaterialModel(getPixel(i, j+1)) == cause ||
                            materialModel.getMaterialModel(getPixel(i, j-1)) == cause) {
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
        g.setColor(materialModel.getColor(mat));
        g.drawLine(x, y, x, y);
    }
}

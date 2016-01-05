package cz.spacks.worms.utilities.materials;

import cz.spacks.worms.main.Application;
import cz.spacks.worms.utilities.MapClass;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.Serializable;

/**
 *
 */
public class MaterialVisuals implements Serializable {

    private MaterialVisuals() {
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
                g.setColor(new Color(source.getRGB(i, j)));
                g.fillRect(i * ratio, j * ratio, Application.BLOCK_SIZE, Application.BLOCK_SIZE);
            }
        }
        g.dispose();
    }
}

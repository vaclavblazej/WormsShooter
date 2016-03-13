package cz.spacks.worms.model.materials;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.Serializable;

/**
 *
 */
public class MaterialVisuals implements Serializable {

    public void redraw(BufferedImage sourceImage, BufferedImage dest) {
        Graphics g = dest.getGraphics();
        g.drawImage(sourceImage, 0, 0, dest.getWidth(), dest.getHeight(), null);

//        int w = sourceImage.getWidth();
//        int h = sourceImage.getHeight();
//        int ratio = dest.getWidth() / w;
//        for (int j = 0; j < h; j++) {
//            for (int i = 0; i < w; i++) {
//                g.setColor(new Color(source.getRGB(i, j)));
//                g.fillRect(i * ratio, j * ratio, Settings.BLOCK_SIZE, Settings.BLOCK_SIZE);
//            }
//        }
        g.dispose();
    }
}

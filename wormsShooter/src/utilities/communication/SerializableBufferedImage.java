package utilities.communication;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.DataBuffer;
import java.awt.image.DataBufferInt;
import java.awt.image.Raster;
import java.awt.image.SinglePixelPackedSampleModel;
import java.awt.image.WritableRaster;
import java.io.Serializable;

/**
 *
 * @author plach_000
 */
public class SerializableBufferedImage implements Serializable {

    private int pixels[];
    private Dimension size;

    public SerializableBufferedImage(BufferedImage image) {
        size = new Dimension(image.getWidth(), image.getHeight());
        pixels = image.getRGB(0, 0, image.getWidth(), image.getHeight(), null, 0, image.getWidth());
    }

    public BufferedImage getImage() {
        int[] bitMasks = new int[]{0xFF0000, 0xFF00, 0xFF, 0xFF000000};
        SinglePixelPackedSampleModel sm = new SinglePixelPackedSampleModel(
                DataBuffer.TYPE_INT, size.width, size.height, bitMasks);
        DataBufferInt db = new DataBufferInt(pixels, pixels.length);
        WritableRaster wr = Raster.createWritableRaster(sm, db, new Point());
        BufferedImage image = new BufferedImage(ColorModel.getRGBdefault(), wr, false, null);
        return image;
    }
}

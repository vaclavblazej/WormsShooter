package cz.spacks.worms.utilities.communication;

import cz.spacks.worms.utilities.AbstractView;

import java.awt.*;
import java.awt.image.*;

/**
 * @author Štìpán Plachý
 */
public class SerializableBufferedImage implements DeseriazibleInto<BufferedImage> {

    private int pixels[];
    private Dimension size;

    public SerializableBufferedImage(BufferedImage image) {
        size = new Dimension(image.getWidth(), image.getHeight());
        pixels = image.getRGB(0, 0, image.getWidth(), image.getHeight(), null, 0, image.getWidth());
    }

    public BufferedImage deserialize() {
        int[] bitMasks = new int[]{0xFF0000, 0xFF00, 0xFF, 0xFF000000};
        SinglePixelPackedSampleModel sm = new SinglePixelPackedSampleModel(
                DataBuffer.TYPE_INT, size.width, size.height, bitMasks);
        DataBufferInt db = new DataBufferInt(pixels, pixels.length);
        WritableRaster wr = Raster.createWritableRaster(sm, db, new Point());
        return new BufferedImage(ColorModel.getRGBdefault(), wr, false, null);
    }

    @Override
    public BufferedImage deserialize(AbstractView view) {
        return deserialize();
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utilities;

import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.IndexColorModel;
import java.awt.image.WritableRaster;
import java.io.Serializable;
import java.util.Hashtable;

/**
 *
 * @author plach_000
 */
public class SerializableBufferedImage implements Serializable {

    public BufferedImage image;

    public SerializableBufferedImage(BufferedImage image) {
        this.image = image;
    }

    public BufferedImage getImage() {
        return image;
    }

}

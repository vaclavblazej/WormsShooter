package utilities;

import java.awt.Color;
import java.awt.color.ColorSpace;

/**
 *
 * @author Skarab
 */
public class ColorExtended extends Color {

    public ColorExtended(int r, int g, int b) {
        super(r, g, b);
    }

    public ColorExtended(int r, int g, int b, int a) {
        super(r, g, b, a);
    }

    public ColorExtended(int rgb) {
        super(rgb);
    }

    public ColorExtended(int rgba, boolean hasalpha) {
        super(rgba, hasalpha);
    }

    public ColorExtended(float r, float g, float b) {
        super(r, g, b);
    }

    public ColorExtended(float r, float g, float b, float a) {
        super(r, g, b, a);
    }

    public ColorExtended(ColorSpace cspace, float[] components, float alpha) {
        super(cspace, components, alpha);
    }

    private String colorToString() {
        char[] buf = new char[7];
        buf[0] = '#';
        String s = Integer.toHexString(getRed());
        if (s.length() == 1) {
            buf[1] = '0';
            buf[2] = s.charAt(0);
        } else {
            buf[1] = s.charAt(0);
            buf[2] = s.charAt(1);
        }
        s = Integer.toHexString(getGreen());
        if (s.length() == 1) {
            buf[3] = '0';
            buf[4] = s.charAt(0);
        } else {
            buf[3] = s.charAt(0);
            buf[4] = s.charAt(1);
        }
        s = Integer.toHexString(getBlue());
        if (s.length() == 1) {
            buf[5] = '0';
            buf[6] = s.charAt(0);
        } else {
            buf[5] = s.charAt(0);
            buf[6] = s.charAt(1);
        }
        return String.valueOf(buf);
    }
}

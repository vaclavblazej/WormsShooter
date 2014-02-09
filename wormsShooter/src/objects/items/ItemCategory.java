package objects.items;

import java.awt.Color;

/**
 *
 * @author Skarab
 */
public enum ItemCategory {

    MATERIAL,
    TOOL;

    public Color gc() {
        switch (this) {
            case MATERIAL:
                return Color.BLACK;
            case TOOL:
                return Color.BLUE;
            default:
                return null;
        }
    }
}

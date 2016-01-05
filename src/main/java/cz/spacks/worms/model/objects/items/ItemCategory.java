package cz.spacks.worms.model.objects.items;

import java.awt.*;

/**
 *
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

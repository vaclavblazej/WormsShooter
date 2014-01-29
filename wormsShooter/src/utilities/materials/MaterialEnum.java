package utilities.materials;

import java.awt.Color;

/**
 *
 * @author Skarab
 */
public enum MaterialEnum {

    AIR,
    WOOD,
    WATER,
    SAND,
    DIRT,
    GRASS,
    STONE;

    public Color getColor() {
        return Material.getColor(this);
    }
}

package utilities.materials;

import java.awt.Color;
import utilities.CollisionState;

/**
 *
 * @author Skarab
 */
public enum MaterialEnum {

    AIR,
    WOOD,
    WATER,
    SAND,
    VINE,
    DIRT,
    GRASS,
    STONE;

    public Color getColor() {
        return Material.getColor(this);
    }
    
    public CollisionState getState(){
        return Material.check(this);
    }
}

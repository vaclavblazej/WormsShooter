package utilities.materials;

import java.awt.Color;
import utilities.CollisionState;

/**
 *
 * @author Skarab
 */
public enum MaterialEnum {

    AIR(0, "#00AAFF", CollisionState.GAS, Opacity.TRANSPARENT),
    WOOD(1, "#808000", CollisionState.GAS, Opacity.OPAQUE),
    WATER(2, "#0000FF", CollisionState.LIQUID, Opacity.SEMITRANSPARENT),
    SAND(3, "#FFFF00", CollisionState.SOLID, Opacity.OPAQUE),
    VINE(4, "#008000", CollisionState.GAS, Opacity.TRANSPARENT),
    DIRT(5, "#804000", CollisionState.SOLID, Opacity.OPAQUE),
    GRASS(6, "#00FF00", CollisionState.SOLID, Opacity.OPAQUE),
    STONE(7, "#AAAAAA", CollisionState.SOLID, Opacity.OPAQUE);
    int value;
    Color color;
    CollisionState state;
    Opacity opacity;

    private MaterialEnum(int value, String color, CollisionState state, Opacity opacity) {
        this.value = value;
        this.color = Color.decode(color);
        this.state = state;
        this.opacity = opacity;
    }

    public int getValue() {
        return value;
    }

    public Color getColor() {
        return color;
    }

    public CollisionState getState() {
        return state;
    }

    public Opacity getOpacity() {
        return opacity;
    }
}

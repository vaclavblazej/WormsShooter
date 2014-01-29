package utilities;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Skarab
 */
public enum Material {

    AIR,
    WOOD,
    WATER,
    SAND,
    DIRT,
    GRASS,
    STONE;
    private static final Map<Material, Elem> material;
    private static final Map<Integer, Material> colors;
    public static final CollisionState DEFAULT = CollisionState.SOLID;

    static {
        material = new HashMap<>();
        colors = new HashMap<>();
        addMaterial(AIR, "#00AAFF", CollisionState.GAS);
        addMaterial(WATER, "#0000FF", CollisionState.LIQUID);
        addMaterial(DIRT, "#804000", CollisionState.SOLID);
        addMaterial(GRASS, "#00FF00", CollisionState.SOLID);
        addMaterial(STONE, "#AAAAAA", CollisionState.SOLID);
        addMaterial(SAND, "#FFFF00", CollisionState.SOLID);
    }

    private static void addMaterial(Material mat, String code, CollisionState state) {
        Color color = Color.decode(code);
        material.put(mat, new Elem(color, state));
        colors.put(color.getRGB(), mat);
    }

    public Color getColor() {
        if (material.get(this) != null) {
            return material.get(this).color;
        }
        return null;
    }

    public static CollisionState check(Color color) {
        Elem result = material.get(colors.get(color.getRGB()));
        return (result != null) ? result.state : DEFAULT;
    }

    private static class Elem {

        public Color color;
        public CollisionState state;

        Elem(Color color, CollisionState state) {
            this.color = color;
            this.state = state;
        }
    }
}

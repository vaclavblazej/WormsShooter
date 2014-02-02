package utilities.materials;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;
import objects.items.ComponentTableModel;
import objects.items.ItemEnum;
import objects.items.ItemFactory;
import server.ServerView;
import utilities.CollisionState;

/**
 *
 * @author Skarab
 */
public class Material {

    private static final Map<MaterialEnum, Elem> material;
    private static final Map<Integer, MaterialEnum> colors;
    public static final CollisionState DEFAULT = CollisionState.SOLID;
    private static final int SOLID_LIGHT = 30;
    private static final int LIQUID_LIGHT = 5;
    private static final int GAS_LIGHT = 0;

    static {
        material = new HashMap<>();
        colors = new HashMap<>();
        ComponentTableModel ctm;
        ItemFactory factory = ServerView.getInstance().getItemFactory();
        addMaterial(MaterialEnum.AIR, "#00AAFF", CollisionState.GAS, null, GAS_LIGHT);
        addMaterial(MaterialEnum.WATER, "#0000FF", CollisionState.LIQUID, null, LIQUID_LIGHT);
        addMaterial(MaterialEnum.DIRT, "#804000", CollisionState.SOLID, null, SOLID_LIGHT);
        addMaterial(MaterialEnum.GRASS, "#00FF00", CollisionState.SOLID, null, SOLID_LIGHT);
        addMaterial(MaterialEnum.VINE, "#008000", CollisionState.GAS, null, SOLID_LIGHT);
        ctm = new ComponentTableModel();
        ctm.add(factory.getBlueprint(ItemEnum.METAL), 2);
        addMaterial(MaterialEnum.STONE, "#AAAAAA", CollisionState.SOLID, ctm, SOLID_LIGHT);
        ctm = new ComponentTableModel();
        ctm.add(factory.getBlueprint(ItemEnum.GUN_POWDER), 5);
        addMaterial(MaterialEnum.SAND, "#FFFF00", CollisionState.SOLID, ctm, SOLID_LIGHT);
    }

    private static void addMaterial(MaterialEnum mat, String code,
            CollisionState state, ComponentTableModel model, int transparency) {
        Color color = Color.decode(code);
        material.put(mat, new Elem(color, state, model, transparency));
        colors.put(color.getRGB(), mat);
    }

    public static Color getColor(MaterialEnum en) {
        if (material.get(en) != null) {
            return material.get(en).color;
        }
        return null;
    }

    public static ComponentTableModel getComponents(MaterialEnum en) {
        if (material.get(en) != null) {
            return material.get(en).model;
        }
        return null;
    }

    public static int getTransparency(MaterialEnum en) {
        if (material.get(en) != null) {
            return material.get(en).transparency;
        }
        return 0;
    }

    public static MaterialEnum getMaterial(Integer i) {
        return colors.get(i);
    }

    public static CollisionState check(Color color) {
        return check(colors.get(color.getRGB()));
    }

    static CollisionState check(MaterialEnum mat) {
        Elem result = material.get(mat);
        return (result != null) ? result.state : DEFAULT;
    }

    private static class Elem {

        public Color color;
        public CollisionState state;
        public ComponentTableModel model;
        public int transparency;

        Elem(Color color, CollisionState state, ComponentTableModel model, int transparency) {
            this.color = color;
            this.state = state;
            this.model = model;
            this.transparency = transparency;
        }
    }
}

package utilities.materials;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;
import objects.items.ComponentTableModel;
import objects.items.ItemEnum;
import objects.items.ItemFactory;
import server.ServerPanel;
import utilities.CollisionState;

/**
 *
 * @author Skarab
 */
public class Material {

    private static final Map<MaterialEnum, Elem> material;
    private static final Map<Integer, MaterialEnum> colors;
    public static final CollisionState DEFAULT = CollisionState.SOLID;

    static {
        material = new HashMap<>();
        colors = new HashMap<>();
        ComponentTableModel ctm;
        ItemFactory factory = ServerPanel.getInstance().getItemFactory();
        addMaterial(MaterialEnum.AIR, "#00AAFF", CollisionState.GAS, null);
        addMaterial(MaterialEnum.WATER, "#0000FF", CollisionState.LIQUID, null);
        addMaterial(MaterialEnum.DIRT, "#804000", CollisionState.SOLID, null);
        addMaterial(MaterialEnum.GRASS, "#00FF00", CollisionState.SOLID, null);
        ctm = new ComponentTableModel();
        ctm.add(factory.getBlueprint(ItemEnum.METAL), 2);
        addMaterial(MaterialEnum.STONE, "#AAAAAA", CollisionState.SOLID, ctm);
        ctm = new ComponentTableModel();
        ctm.add(factory.getBlueprint(ItemEnum.GUN_POWDER), 5);
        addMaterial(MaterialEnum.SAND, "#FFFF00", CollisionState.SOLID, ctm);
    }

    private static void addMaterial(MaterialEnum mat, String code,
            CollisionState state, ComponentTableModel model) {
        Color color = Color.decode(code);
        material.put(mat, new Elem(color, state, model));
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

    public static MaterialEnum getMaterial(Integer i) {
        return colors.get(i);
    }

    public static CollisionState check(Color color) {
        Elem result = material.get(colors.get(color.getRGB()));
        return (result != null) ? result.state : DEFAULT;
    }

    private static class Elem {

        public Color color;
        public CollisionState state;
        public ComponentTableModel model;

        Elem(Color color, CollisionState state, ComponentTableModel model) {
            this.color = color;
            this.state = state;
            this.model = model;
        }
    }
}

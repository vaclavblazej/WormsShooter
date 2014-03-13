package utilities.materials;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;
import objects.items.ComponentTableModel;
import objects.items.ItemEnum;
import objects.items.ItemFactory;
import utilities.AbstractView;
import utilities.CollisionState;

/**
 *
 * @author Skarab
 */
public class Material {

    public static final CollisionState DEFAULT = CollisionState.SOLID;
    private static final int SOLID_LIGHT = 30;
    private static final int LIQUID_LIGHT = 5;
    private static final int GAS_LIGHT = 2;
    private final Map<MaterialEnum, ComponentTableModel> components;

    public Material(ItemFactory factory) {
        components = new HashMap<>();
        ComponentTableModel ctm;
        addMaterial(MaterialEnum.AIR, null);
        addMaterial(MaterialEnum.WATER, null);
        addMaterial(MaterialEnum.DIRT, null);
        addMaterial(MaterialEnum.GRASS, null);
        addMaterial(MaterialEnum.VINE, null);
        ctm = new ComponentTableModel();
        ctm.add(factory.getBlueprint(ItemEnum.METAL), 2);
        addMaterial(MaterialEnum.STONE, ctm);
        ctm = new ComponentTableModel();
        ctm.add(factory.getBlueprint(ItemEnum.GUN_POWDER), 5);
        addMaterial(MaterialEnum.SAND, ctm);
    }

    private void addMaterial(MaterialEnum mat, ComponentTableModel model) {
        components.put(mat, model);
    }

    public ComponentTableModel getComponents(MaterialEnum en) {
        return components.get(en);
    }

    public MaterialEnum getByColor(int number) {
        for (MaterialEnum me : components.keySet()) {
            if (me.getColor().getRGB() == number) {
                return me;
            }
        }
        return null;
    }

    public CollisionState getState(MaterialEnum mat) {
        CollisionState result = mat.getState();
        return (result != null) ? result : DEFAULT;
    }
}

package cz.spacks.worms.controller.materials;

import cz.spacks.worms.model.objects.ItemsCount;
import cz.spacks.worms.model.objects.Material;
import cz.spacks.worms.model.objects.items.ItemEnum;
import cz.spacks.worms.model.objects.items.ItemFactory;
import cz.spacks.worms.controller.AbstractView;
import cz.spacks.worms.controller.properties.CollisionState;

import java.awt.*;
import java.util.*;
import java.util.List;

/**
 *
 */
public class MaterialModel {
    public static final CollisionState DEFAULT = CollisionState.SOLID;
    private static final int SOLID_LIGHT = 30;
    private static final int LIQUID_LIGHT = 5;
    private static final int GAS_LIGHT = 2;

    private final Map<MaterialEnum, Material> material;
    private final Map<Integer, MaterialEnum> colors;

    public MaterialModel(AbstractView view) {
        material = new HashMap<>();
        colors = new HashMap<>();
        ItemFactory factory = view.getItemFactory();
        addMaterial(MaterialEnum.AIR, "#00AAFF", CollisionState.GAS, GAS_LIGHT);
        addMaterial(MaterialEnum.WATER, "#0000FF", CollisionState.LIQUID, LIQUID_LIGHT);
        addMaterial(MaterialEnum.DIRT, "#804000", CollisionState.SOLID, SOLID_LIGHT);
        addMaterial(MaterialEnum.GRASS, "#00FF00", CollisionState.SOLID, SOLID_LIGHT);
        addMaterial(MaterialEnum.VINE, "#008000", CollisionState.GAS, SOLID_LIGHT);
        List<ItemsCount> stoneItems = new ItemsCount.Builder().add(factory.getBlueprint(ItemEnum.METAL), 2).build();
        addMaterial(MaterialEnum.STONE, "#AAAAAA", CollisionState.SOLID, SOLID_LIGHT, stoneItems);
        List<ItemsCount> sandItems = new ItemsCount.Builder().add(factory.getBlueprint(ItemEnum.GUN_POWDER), 5).build();
        addMaterial(MaterialEnum.SAND, "#FFFF00", CollisionState.SOLID, SOLID_LIGHT, sandItems);
    }

    private void addMaterial(MaterialEnum mat, String code,
                             CollisionState state, int transparency) {
        addMaterial(mat, code, state, transparency, new ArrayList<>());
    }

    private void addMaterial(MaterialEnum mat, String code,
                             CollisionState state, int transparency, List<ItemsCount> minedItems) {
        Color color = Color.decode(code);
        material.put(mat, new Material(color, state, transparency, minedItems));
        colors.put(color.getRGB(), mat);
    }

    public Color getColor(MaterialEnum en) {
        if (material.get(en) != null) {
            return material.get(en).color;
        }
        return null;
    }

    public int getTransparency(MaterialEnum en) {
        if (material.get(en) != null) {
            return material.get(en).transparency;
        }
        return 0;
    }

    public MaterialEnum getMaterial(Integer i) {
        return colors.get(i);
    }

    public CollisionState getState(Color color) {
        return getState(colors.get(color.getRGB()));
    }

    public CollisionState getState(MaterialEnum mat) {
        Material result = material.get(mat);
        return (result != null) ? result.state : DEFAULT;
    }

    public List<ItemsCount> getComponents(MaterialEnum mat) {
        return material.get(mat).minedItems;
    }
}

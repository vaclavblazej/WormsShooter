package cz.spacks.worms.model.map;

import cz.spacks.worms.controller.materials.MaterialEnum;
import cz.spacks.worms.controller.properties.CollisionState;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class Chunk {

    public static Chunk NULL;

    static {
        final ArrayList<MaterialAmount> materials1 = new ArrayList<>();
        final Material material = new Material(MaterialEnum.STONE, Color.RED, CollisionState.SOLID, 0, new ArrayList<>());
        materials1.add(new MaterialAmount(material, 1));
        NULL = new Chunk(materials1);
    }

    private List<MaterialAmount> materials;

    public Chunk(List<MaterialAmount> materials) {
        this.materials = materials;
    }

    public List<MaterialAmount> getMaterials() {
        return materials;
    }
}

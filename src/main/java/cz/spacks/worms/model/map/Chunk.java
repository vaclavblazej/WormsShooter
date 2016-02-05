package cz.spacks.worms.model.map;

import java.util.List;

/**
 *
 */
public class Chunk {

    private List<MaterialAmount> materials;

    public Chunk(List<MaterialAmount> materials) {
        this.materials = materials;
    }

    public List<MaterialAmount> getMaterials() {
        return materials;
    }
}

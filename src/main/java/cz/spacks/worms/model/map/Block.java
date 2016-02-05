package cz.spacks.worms.model.map;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class Block {

    private List<Chunk> chunks;

    public Block() {
        chunks = new ArrayList<>();
    }

    public List<Chunk> getChunks() {
        return chunks;
    }
}

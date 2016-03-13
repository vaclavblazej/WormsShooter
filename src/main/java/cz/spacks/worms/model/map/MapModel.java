package cz.spacks.worms.model.map;

import cz.spacks.worms.model.materials.MaterialEnum;
import cz.spacks.worms.model.materials.MaterialModel;
import cz.spacks.worms.model.structures.Map2D;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 */
public class MapModel {

    private static final Logger logger = Logger.getLogger(MapModel.class.getName());

    private Map2D<Chunk> map;
    private Dimension dimensions;

    private MaterialModel materialModelCache;
    private List<MapModelListener> listeners;

    public MapModel(Dimension dimension) {
        this(dimension, new Map2D<>());
    }

    public MapModel(Dimension dimension, Map2D<Chunk> map) {
        this.map = map;
        this.dimensions = dimension;
        this.listeners = new ArrayList<>();
    }

    public void addChunk(Chunk chunk, Point position) {
        if (position.x >= 0 && position.x < dimensions.width && position.y >= 0 && position.y < dimensions.height) {
            map.remove(position.x, position.y);
            map.put(position.x, position.y, chunk);
            dataChanged(position);
        } else {
            logger.log(Level.SEVERE, "Tried to put chunk "
                    + chunk.toString()
                    + " on position "
                    + position.toString()
                    + ", but position must be between 0 and "
                    + dimensions.toString());
        }
    }

    public Chunk destroyChunk(Point position) {
        final Chunk chunk = this.getChunk(position);
        if(chunk != Chunk.NULL) {
            final Chunk newChunk = materialModelCache.getChunk(MaterialEnum.AIR);
            this.addChunk(newChunk, position);
        }
        return chunk;
    }

    public Map2D<Chunk> getMap() {
        return map;
    }

    public Chunk getChunk(Point position) {
        final Chunk chunk = map.get(position.x, position.y);
        if (chunk == null) {
            logger.log(Level.SEVERE, "Tried to get chunk from position "
                    + position.toString()
                    + ", but position must be between 0 and "
                    + dimensions.toString());
            return Chunk.NULL;
        }
        return chunk;
    }

    public Dimension getDimensions() {
        return dimensions;
    }

    private void dataChanged(Point position) {
        for (MapModelListener listener : listeners) {
            listener.chunkUpdated(position);
        }
    }

    public void addListener(MapModelListener listener) {
        listeners.add(listener);
        listener.setModel(this);
    }

    public void removeListener(MapModelListener listener) {
        listeners.remove(listener);
    }

    public void setMaterialModelCache(MaterialModel materialModelCache) {
        this.materialModelCache = materialModelCache;
    }
}

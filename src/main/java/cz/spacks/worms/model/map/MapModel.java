package cz.spacks.worms.model.map;

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

    private List<MapModelListener> listeners;

    public MapModel(Dimension dimension) {
        this.map = new Map2D<>();
        this.dimensions = dimension;
        this.listeners = new ArrayList<>();
    }

    public MapModel(Dimension dimension, Map2D<Chunk> map) {
        this.map = map;
        this.dimensions = dimension;
        this.listeners = new ArrayList<>();
    }

    public void addChunk(Chunk chunk, Point position) {
        if (position.x >= 0 && position.x < dimensions.width && position.y >= 0 && position.y < dimensions.height) {
            map.put(position.x, position.y, chunk);
        } else {
            logger.log(Level.SEVERE, "Tried to put chunk "
                    + chunk.toString()
                    + " on position "
                    + position.toString()
                    + ", but position must be between 0 and "
                    + dimensions.toString());
        }
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

    public void addListener(MapModelListener listener) {
        listeners.add(listener);
        listener.setModel(this);
    }

    public void removeListener(MapModelListener listener) {
        listeners.remove(listener);
    }
}

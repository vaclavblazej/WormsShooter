package cz.spacks.worms.model.map;

import java.awt.*;

/**
 *
 */
public interface MapModelListener {

    void chunkUpdated(Point position);

    void areaUpdated(Point start, Point end);

    void setModel(MapModel mapModel);
}

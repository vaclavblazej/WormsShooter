package cz.spacks.worms.model.objects;

import cz.spacks.worms.controller.services.WorldService;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.io.Serializable;

/**
 *
 */
public interface GraphicComponent extends Serializable {

    void draw(Graphics2D g);

    void tick(WorldService worldService);

    void drawRelative(Graphics2D g, AffineTransform trans);
}

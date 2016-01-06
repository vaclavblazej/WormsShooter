package cz.spacks.worms.model.objects;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.io.Serializable;

/**
 *
 */
public interface GraphicComponent extends Serializable {

    void draw(Graphics2D g);

    void tick();

    void drawRelative(Graphics2D g, AffineTransform trans);
}
package cz.spacks.worms.model.objects;

import cz.spacks.worms.controller.AbstractView;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.io.Serializable;

/**
 *
 */
public interface GraphicComponent extends Serializable {

    void draw(Graphics2D g);

    void tick(AbstractView view);

    void drawRelative(Graphics2D g, AffineTransform trans);
}

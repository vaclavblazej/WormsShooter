package objects;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.io.Serializable;

/**
 *
 * @author Skarab
 */
public interface GraphicComponent extends Serializable {

    void draw(Graphics2D g);

    void tick();

    void drawRelative(Graphics2D g, AffineTransform trans);
}

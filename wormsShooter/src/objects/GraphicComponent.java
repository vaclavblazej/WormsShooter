package objects;

import java.awt.Graphics2D;
import java.io.Serializable;

/**
 *
 * @author Skarab
 */
public interface GraphicComponent extends Serializable {

    void draw(Graphics2D g);
}

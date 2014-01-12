package main;

import java.awt.Graphics2D;
import java.awt.image.ImageObserver;

/**
 *
 * @author Skarab
 */
public interface GraphicComponent {

    void draw(Graphics2D g, ImageObserver obs);
}

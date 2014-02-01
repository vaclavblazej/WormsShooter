package objects;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import spritesheets.Frame;

/**
 *
 * @author Skarab
 */
public class Miscellaneous implements GraphicComponent {

    private Frame image;
    private Point position;
    private AffineTransform tr;

    public Miscellaneous(Frame image, int x, int y) {
        this.image = image;
        this.position = new Point(x, y);
        this.tr = new AffineTransform();
    }

    @Override
    public void draw(Graphics2D g) {
        tr.setToTranslation(position.x, position.y);
        g.drawImage(image.getFrame(), tr, null);
    }
}

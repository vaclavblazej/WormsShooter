package objects;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

/**
 *
 * @author Skarab
 */
public class Part implements GraphicComponent {

    private BufferedImage image;
    private AffineTransform transform;

    public Part(BufferedImage image) {
        this.image = image;
        transform = new AffineTransform();
    }

    public void rotate(double theta) {
        transform.rotate(theta);
    }

    public void translage(double tx, double ty) {
        transform.translate(tx, ty);
    }

    @Override
    public void draw(Graphics2D g) {
        g.drawImage(image, transform, null);
    }
}

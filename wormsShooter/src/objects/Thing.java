package objects;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.image.BufferedImage;
import java.util.logging.Level;
import java.util.logging.Logger;
import main.Main;
import utilities.communication.SerializableBufferedImage;

/**
 *
 * @author Skarab
 */
public class Thing implements GraphicComponent {

    private Point size;
    private Point position;
    private AffineTransform tr;
    private SerializableBufferedImage image;
    private transient BufferedImage cache = null;

    public Thing(SerializableBufferedImage img, Point size, Point position) {
        this.image = img;
        this.size = size;
        this.position = position;
    }

    @Override
    public void draw(Graphics2D g) {
        g.setColor(Color.YELLOW);
        g.drawRect(position.x / Main.RATIO, position.y / Main.RATIO, size.x, size.y);
    }

    @Override
    public void tick() {
    }

    @Override
    public void drawRelative(Graphics2D g, AffineTransform trans) {
        tr = (AffineTransform) trans.clone();
        try {
            tr.invert();
        } catch (NoninvertibleTransformException ex) {
            Logger.getLogger(Bullet.class.getName()).log(Level.SEVERE, null, ex);
        }
        tr.translate(position.x, position.y);
        g.setTransform(tr);
        if (cache == null) {
            cache = image.getImage();
        }
        g.drawImage(cache, null, null);
    }
}

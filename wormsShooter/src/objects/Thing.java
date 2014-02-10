package objects;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.image.BufferedImage;
import java.util.logging.Level;
import java.util.logging.Logger;
import utilities.communication.SerializableBufferedImage;
import utilities.spritesheets.Animation;

/**
 *
 * @author Skarab
 */
public class Thing implements GraphicComponent {

    private Animation animation;
    private Point size;
    private Point position;
    private AffineTransform tr;
    private SerializableBufferedImage image;
    private boolean hasImage;

    public Thing(Animation animation, Point size, Point position) {
        this.animation = animation;
        this.size = size;
        this.position = position;
        this.animation.start();
        hasImage = false;
    }

    public Thing(SerializableBufferedImage img, Point size, Point position) {
        this.image = img;
        this.size = size;
        this.position = position;
        hasImage = true;
    }

    @Override
    public void draw(Graphics2D g) {
        if (hasImage) {
            BufferedImage img = image.getImage();
            g.drawImage(img, null, null);
        } else {
            g.drawImage(animation.getSprite(), null, null);
        }
    }

    @Override
    public void tick() {
        animation.update();
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
        g.setColor(Color.RED);
        g.fillRect(0, 0, 10, 3);
    }
}

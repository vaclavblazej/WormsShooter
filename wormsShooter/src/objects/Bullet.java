package objects;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.awt.geom.NoninvertibleTransformException;
import java.util.logging.Level;
import java.util.logging.Logger;
import main.Main;

/**
 *
 * @author Skarab
 */
public class Bullet implements GraphicComponent {

    private static final int velocity = 2;
    private AffineTransform tr;
    private Point.Double position;
    private double rotation;

    public Bullet(Point position, double rotation) {
        this.position = new Point.Double(position.x, position.y);
        this.rotation = rotation;
    }

    @Override
    public void draw(Graphics2D g) {
        tr = new AffineTransform();
        tr.translate(position.x / Main.RATIO, position.y / Main.RATIO);
        tr.rotate(rotation);
        g.setColor(Color.RED);
        g.setTransform(tr);
        g.fillRect(0, 0, 10, 3);
    }

    @Override
    public void drawRelative(Graphics2D g, AffineTransform trans) {
        AffineTransform tr = (AffineTransform) trans.clone();
        try {
            tr.invert();
        } catch (NoninvertibleTransformException ex) {
            Logger.getLogger(Bullet.class.getName()).log(Level.SEVERE, null, ex);
        }
        tr.translate(position.x, position.y);
        tr.rotate(rotation);
        g.setTransform(tr);
        g.setColor(Color.RED);
        g.fillRect(0, 0, 10, 3);
    }

    @Override
    public void tick() {
        position.x += velocity * Math.cos(rotation);
        position.y += velocity * Math.sin(rotation);
    }
}

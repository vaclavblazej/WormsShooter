package objects;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.awt.geom.NoninvertibleTransformException;
import java.util.logging.Level;
import java.util.logging.Logger;
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

    public Thing(Animation animation, Point size, Point position) {
        this.animation = animation;
        this.size = size;
        this.position = position;
        this.animation.start();
    }


    @Override
    public void draw(Graphics2D g) {
        g.drawImage(animation.getSprite(), null, null);
    }

    @Override
    public void tick() {
        animation.update();
    }

    @Override
    public void drawRelative(Graphics2D g, AffineTransform trans) {
        tr = (AffineTransform) trans.clone();
        tr.translate(position.x, position.y);
        g.setTransform(tr);
        g.setColor(Color.RED);
        g.fillRect(0, 0, 10, 3);
    }
}
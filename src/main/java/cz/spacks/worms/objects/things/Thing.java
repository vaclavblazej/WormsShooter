package cz.spacks.worms.objects.things;

import cz.spacks.worms.objects.GraphicComponent;
import cz.spacks.worms.utilities.spritesheets.Animation;

import java.awt.*;
import java.awt.geom.AffineTransform;

/**
 *
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

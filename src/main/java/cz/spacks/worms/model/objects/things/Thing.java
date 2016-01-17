package cz.spacks.worms.model.objects.things;

import cz.spacks.worms.model.objects.GraphicComponent;
import cz.spacks.worms.model.spritesheets.Animation;
import cz.spacks.worms.view.views.AbstractView;

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
    public void tick(AbstractView view) {
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

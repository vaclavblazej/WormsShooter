package objects;

import main.Application;
import utilities.AbstractView;
import utilities.CollisionState;

import java.awt.*;
import java.awt.geom.AffineTransform;

/**
 * @author Václav Blažej
 */
public class Bullet implements GraphicComponent {

    private static final int velocity = 20;
    private AffineTransform tr;
    private Point.Double position;
    private double rotation;
    private AbstractView view;

    public Bullet(Point position, double rotation, AbstractView view) {
        this.position = new Point.Double(position.x, position.y);
        this.rotation = rotation;
        this.view = view;
    }

    @Override
    public void draw(Graphics2D g) {
        tr = new AffineTransform();
        tr.translate(position.x / Application.RATIO, position.y / Application.RATIO);
        tr.rotate(rotation);
        g.setColor(Color.RED);
        g.setTransform(tr);
        g.fillRect(0, 0, 10, 3);
    }

    @Override
    public void drawRelative(Graphics2D g, AffineTransform trans) {
        tr = (AffineTransform) trans.clone();
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
        Color pixel = view.getPixel((int) (position.x / Application.RATIO), (int) (position.y / Application.RATIO));
        CollisionState check = view.getMaterial().getState(pixel);
        if (check != CollisionState.GAS) {
            view.removeObject(this);
        }
    }
}

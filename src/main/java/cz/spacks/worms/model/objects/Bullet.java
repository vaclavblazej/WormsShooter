package cz.spacks.worms.model.objects;

import cz.spacks.worms.Application;
import cz.spacks.worms.controller.AbstractView;
import cz.spacks.worms.controller.CollisionState;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;

/**
 *
 */
public class Bullet implements GraphicComponent {

    private Point.Double velocity;
    private AffineTransform tr;
    private Point.Double position;
    private double rotation;
    private AbstractView view;

    public Bullet(Point position, double rotation, AbstractView view) {
        int vel = 100;
        velocity = new Point2D.Double();
        velocity.x = vel * Math.cos(rotation);
        velocity.y = vel * Math.sin(rotation);
        this.position = new Point.Double(position.x, position.y);
        this.rotation = rotation;
        this.view = view;
    }

    @Override
    public void draw(Graphics2D g) {
        tr = new AffineTransform();
        tr.translate(position.x / Application.BLOCK_SIZE, position.y / Application.BLOCK_SIZE);
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
        velocity.y += 1;
        position.x += velocity.x;
        position.y += velocity.y;
        Color pixel = view.getPixel((int) (position.x / Application.BLOCK_SIZE), (int) (position.y / Application.BLOCK_SIZE));
        CollisionState check = view.getMaterial().getState(pixel);
        if (check != CollisionState.GAS) {
            view.removeObject(this);
        }
    }
}

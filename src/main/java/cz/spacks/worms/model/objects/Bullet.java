package cz.spacks.worms.model.objects;

import cz.spacks.worms.controller.Settings;
import cz.spacks.worms.controller.properties.CollisionState;
import cz.spacks.worms.controller.services.WorldService;
import cz.spacks.worms.view.views.AbstractView;

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

    public Bullet(Point position, double rotation) {
        int vel = 100;
        velocity = new Point2D.Double();
        velocity.x = vel * Math.cos(rotation);
        velocity.y = vel * Math.sin(rotation);
        this.position = new Point.Double(position.x, position.y);
        this.rotation = rotation;
    }

    @Override
    public void draw(Graphics2D g) {
        tr = new AffineTransform();
        tr.translate(position.x / Settings.BLOCK_SIZE, position.y / Settings.BLOCK_SIZE);
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
    public void tick(WorldService worldService) {
        velocity.y += 1;
        position.x += velocity.x;
        position.y += velocity.y;
        Color pixel = worldService.getPixel((int) (position.x / Settings.BLOCK_SIZE), (int) (position.y / Settings.BLOCK_SIZE));
        CollisionState check = worldService.getMaterialModel().getState(pixel);
    }
}

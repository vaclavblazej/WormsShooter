package cz.spacks.worms.model.objects;

import cz.spacks.worms.controller.Settings;
import cz.spacks.worms.model.objects.items.ItemBlueprint;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;

/**
 *
 */
public class Body {

    private Point position;
    private Point.Double velocity;
    private Dimension bodyBlockSize;
    private Inventory inventory;
    private ItemBlueprint heldItem;

    public Body() {
        this(new Point(0, 0), new Point2D.Double(0, 0), new Dimension());
    }

    public Body(Point position,
                Point.Double velocity,
                Dimension bodyBlockSize) {
        this.position = position;
        this.velocity = velocity;
        this.bodyBlockSize = bodyBlockSize;
        this.inventory = new Inventory();
        this.heldItem = null;
    }

    public Body(int x, int y) {
        this(new Point(x, y), new Point.Double(0, 0), new Dimension(1, 2));
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void setPosition(Point point) {
        this.position = point;
    }

    public Point getPosition() {
        return position;
    }

    public Point2D.Double getVelocity() {
        return velocity;
    }

    public Dimension getBodyBlockSize() {
        return bodyBlockSize;
    }
    public void setVelocity(Point.Double point) {
        this.velocity.x = point.x;
        this.velocity.y = point.y;
    }

    public ItemBlueprint getHeldItem() {
        return heldItem;
    }
}

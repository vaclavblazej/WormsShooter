package objects;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Point2D;
import objects.items.ComponentTableModel;
import objects.items.Item;
import utilities.CollisionState;
import utilities.ViewInterface;
import utilities.communication.Action;
import utilities.communication.SerializableBody;

/**
 *
 * @author Skarab
 */
public class TestBody implements GraphicComponent {

    private static final double JUMP = 0.6;
    private static final int INITIAL_HEALTH = 100;
    private Point.Double position;
    private Point.Double velocity;
    private Action movement;
    private Dimension SIZE;
    private Dimension REAL_SIZE;
    private int ratio;
    private boolean jump;
    private ViewInterface map;
    private ComponentTableModel inventory;
    private CollisionState state;
    private boolean alive;
    private int health;

    public TestBody(Point2D.Double position, Point2D.Double velocity,
            Action movement, Dimension REAL_SIZE,
            boolean jump, ViewInterface map) {
        this.position = position;
        this.velocity = velocity;
        this.movement = movement;
        this.REAL_SIZE = REAL_SIZE;
        this.ratio = map.getRatio();
        this.SIZE = new Dimension(REAL_SIZE.width * ratio, REAL_SIZE.height * ratio);
        this.jump = jump;
        this.map = map;
        this.inventory = new ComponentTableModel("Item", "Count");
        this.alive = false;
    }

    public TestBody(int x, int y, ViewInterface map) {
        this(new Point.Double(x, y), new Point.Double(0, 0),
                Action.MOVE_STOP, new Dimension(1, 2), false, map);
    }

    public ComponentTableModel getInventory() {
        return inventory;
    }

    public void addItem(Item item) {
        inventory.add(item, 1);
    }

    public void setPosition(Point point) {
        this.position.x = point.x;
        this.position.y = point.y;
    }

    public Point getPosition() {
        return new Point((int) position.x, (int) position.y);
    }

    public Point2D.Double getVelocity() {
        return velocity;
    }

    public void setVelocity(Point.Double point) {
        this.velocity.x = point.x;
        this.velocity.y = point.y;
    }

    public void fallBy(double y) {
        int directionY = (velocity.y >= 0) ? 1 : -1;
        double absoluteY = Math.abs(y);
        int i;
        if (absoluteY < 0.01) { // helps but does not fix completely
            return;
        }
        for (i = 1; i <= absoluteY; i++) {
            if (map.check((int) position.x, (int) position.y + REAL_SIZE.height + i * directionY)
                    == CollisionState.SOLID) {
                velocity.y = 0;
                break;
            }
        }
        position.y += i * directionY;
    }

    public void tick() {
        int directionY = (velocity.y >= 0) ? 1 : -1;
        state = map.check((int) position.x, (int) position.y + REAL_SIZE.height - 1 + directionY);
        switch (state) {
            case GAS:
                velocity.y += 0.1;
                fallBy(velocity.y);
                break;
            case LIQUID:
                velocity.y += 0.04;
                fallBy(velocity.y);
                jump = true;
                break;
            case SOLID:
                velocity.y = 0;
                jump = true;
                break;
        }
        if (map.check((int) position.x + 1, (int) position.y + 1) != CollisionState.SOLID) {
            if (movement.equals(Action.MOVE_RIGHT)) {
                position.x += 1;
            }
        }
        if (map.check((int) position.x - 1, (int) position.y + 1) != CollisionState.SOLID) {
            if (movement.equals(Action.MOVE_LEFT)) {
                position.x -= 1;
            }
        }
    }

    public void control(Action action) {
        switch (action) {
            case MOVE_RIGHT:
            case MOVE_LEFT:
            case MOVE_STOP:
                movement = action;
                break;
            case MOVE_JUMP:
                if (jump == true) {
                    velocity.y -= JUMP;
                    jump = false;
                }
                break;
        }
    }

    public boolean isAlive() {
        return alive;
    }

    public void spawn() {
        alive = true;
        health = INITIAL_HEALTH;
    }

    public void die() {
        alive = false;
    }

    public void heal(int heal) {
        health += heal;
    }

    public void damage(int damage) {
        health -= damage;
        if (health <= 0) {
            die();
        }
    }

    @Override
    public void draw(Graphics2D g) {
        g.setColor(Color.RED);
        g.fillRect((int) position.x, (int) position.y, SIZE.width, SIZE.height);
    }

    public void drawRelative(Graphics2D g) {
        g.setColor(Color.RED);
        g.fillRect(0, 0, SIZE.width, SIZE.height);
    }

    public SerializableBody serialize() {
        return new SerializableBody(position, velocity, movement, REAL_SIZE, jump);
    }
}

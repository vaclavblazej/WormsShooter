package objects;

import utilities.CollisionState;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Point2D;
import static utilities.CollisionState.FREE;
import objects.items.Inventory;
import objects.items.Item;
import utilities.MapInterface;
import utilities.communication.Action;
import utilities.communication.SerializableBody;

/**
 *
 * @author Skarab
 */
public class TestBody implements GraphicComponent {

    private static final double JUMP = 0.6;
    private Point.Double position;
    private Point.Double velocity;
    private Action movement;
    private Dimension SIZE;
    private Dimension REAL_SIZE;
    private int ratio;
    private boolean jump;
    private MapInterface map;
    private Inventory inventory;

    public TestBody(Point2D.Double position, Point2D.Double velocity,
            Action movement, Dimension REAL_SIZE,
            boolean jump, MapInterface map) {
        this.position = position;
        this.velocity = velocity;
        this.movement = movement;
        this.REAL_SIZE = REAL_SIZE;
        this.ratio = map.getRatio();
        this.SIZE = new Dimension(REAL_SIZE.width * ratio, REAL_SIZE.height * ratio);
        this.jump = jump;
        this.map = map;
        this.inventory = new Inventory();
    }

    public TestBody(int x, int y, MapInterface map) {
        this(new Point.Double(x, y), new Point.Double(0, 0),
                Action.MOVE_STOP, new Dimension(1, 2), false, map);
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void addItem(Item item) {
        inventory.addItem(item);
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
        double absoluteY = Math.abs(y) + 0.00001;
        int i;
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
        switch (map.check((int) position.x, (int) position.y + REAL_SIZE.height - 1 + directionY)) {
            case FREE:
                velocity.y += 0.1;
                fallBy(velocity.y);
                break;
            case SOLID:
                velocity.y = 0;
                jump = true;
                break;
        }
        if (map.check((int) position.x + 1, (int) position.y + 1) == CollisionState.FREE) {
            if (movement.equals(Action.MOVE_RIGHT)) {
                position.x += 1;
            }
        }
        if (map.check((int) position.x - 1, (int) position.y + 1) == CollisionState.FREE) {
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

package objects;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import main.Main;
import objects.items.InventoryTableModel;
import objects.items.ItemBlueprint;
import utilities.AbstractView;
import utilities.CollisionState;
import utilities.communication.SerializableBody;
import utilities.spritesheets.Animation;
import utilities.spritesheets.Frame;
import utilities.spritesheets.SpriteLoader;

/**
 *
 * @author Skarab
 */
public class Body implements GraphicComponent {

    private static final double JUMP = 6;
    private static final double GRAVITY = 0.1;
    private static final int SPEED = 8;
    private static final int INITIAL_HEALTH = 100;
    private Point position;
    private Point.Double velocity;
    private MoveAction movement;
    private Dimension SIZE;
    private Dimension REAL_SIZE;
    private int ratio;
    private boolean jump;
    private AbstractView view;
    private InventoryTableModel inventory;
    private CollisionState state;
    private CollisionState belowState;
    private boolean alive;
    private int health;
    private Animation animation;

    public Body(Point2D.Double position, Point2D.Double velocity,
            MoveAction movement, Dimension REAL_SIZE,
            boolean jump, AbstractView view) {
        this.position = new Point((int) (position.x * Main.RATIO),
                (int) (position.y * Main.RATIO));
        this.velocity = velocity;
        this.movement = movement;
        this.REAL_SIZE = REAL_SIZE;
        this.ratio = view.getRatio();
        this.SIZE = new Dimension(REAL_SIZE.width * ratio, REAL_SIZE.height * ratio);
        this.jump = jump;
        this.view = view;
        this.inventory = new InventoryTableModel("Item", "Count");
        this.alive = false;
        SpriteLoader.loadSprite("Materials");
        SpriteLoader.set(20, 40);
        ArrayList<Frame> frames = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            frames.add(SpriteLoader.getSprite(i, 0));
        }
        this.animation = new Animation(frames);
        animation.start();
    }

    public Body(int x, int y, AbstractView map) {
        this(new Point.Double(x, y), new Point.Double(0, 0),
                MoveAction.STOP, new Dimension(1, 2), false, map);
    }

    public InventoryTableModel getInventory() {
        return inventory;
    }

    public void addItem(ItemBlueprint item) {
        inventory.add(item, 1);
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
            if (view.check(position.x, position.y + REAL_SIZE.height
                    + (int) (i * directionY * Main.RATIO))
                    == CollisionState.SOLID) {
                velocity.y = 0;
                break;
            }
        }
        position.y += i * directionY * Main.RATIO;
    }

    public void tick() {
        int directionY = (velocity.y >= 0) ? 1 : -1;
        state = view.check(position.x, position.y + REAL_SIZE.height);
        belowState = view.check(position.x, position.y + REAL_SIZE.height - 1 + directionY);
        /*switch (belowState) {
            case GAS:
                //velocity.y += GRAVITY;
                fallBy(velocity.y);
                break;
            case LIQUID:
                //velocity.y += GRAVITY / 4;
                fallBy(velocity.y);
                jump = true;
                break;
            case SOLID:
                velocity.y = 0;
                jump = true;
                break;
        }*/
        if (view.check(position.x , position.y + 1) != CollisionState.SOLID &&
                view.check(position.x , position.y + 1) != CollisionState.SOLID) {
            if (movement.equals(MoveAction.RIGHT)) {
                position.x += SPEED;
                animation.setDirection(1);
                animation.update();
            }
        }
        if (view.check(position.x, position.y + 1) != CollisionState.SOLID) {
            if (movement.equals(MoveAction.LEFT)) {
                position.x -= SPEED;
                animation.setDirection(-1);
                animation.update();
            }
        }
    }

    public void control(MoveAction action) {
        switch (action) {
            case RIGHT:
            case LEFT:
            case STOP:
                movement = action;
                break;
            case JUMP:
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
        g.fillRect(
                (int) (position.x / Main.RATIO),
                (int) (position.y / Main.RATIO),
                SIZE.width, SIZE.height);
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
        //tr.rotate(rotation);
        g.setTransform(tr);
        g.setColor(Color.RED);
        g.fillRect(0, 0, SIZE.width, SIZE.height);
        g.drawImage(animation.getSprite(), null, null);
    }

    public SerializableBody serialize() {
        return new SerializableBody(
                new Point.Double(position.x / Main.RATIO, position.y / Main.RATIO),
                velocity, movement, REAL_SIZE, jump);
    }
}

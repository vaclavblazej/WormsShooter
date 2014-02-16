package objects;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.util.ArrayList;
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

    private static final double JUMP = 12;
    private static final double GRAVITY = 1;
    private static final int SPEED = 8;
    private static final int INITIAL_HEALTH = 100;
    private Point position;
    private Point.Double velocity;
    private MoveEnum movement;
    private Dimension PHYSICS_SIZE;
    private Dimension VIEW_SIZE;
    private Dimension REAL_SIZE;
    private boolean jump;
    private AbstractView view;
    private InventoryTableModel inventory;
    private CollisionState state;
    private CollisionState leftVerticalCollision;
    private CollisionState rightVerticalCollision;
    private CollisionState topSideCollision;
    private CollisionState bottomSideCollision;
    private boolean alive;
    private int health;
    private Animation animation;

    public Body(Point2D.Double position, Point2D.Double velocity,
            MoveEnum movement, Dimension REAL_SIZE,
            boolean jump, AbstractView view) {
        this.position = new Point((int) (position.x * Main.RATIO),
                (int) (position.y * Main.RATIO));
        this.velocity = velocity;
        this.movement = movement;
        this.REAL_SIZE = REAL_SIZE;
        int ratio = view.getRatio();
        this.PHYSICS_SIZE = new Dimension((int) (REAL_SIZE.width * Main.RATIO),
                (int) (REAL_SIZE.height * Main.RATIO));
        this.VIEW_SIZE = new Dimension(REAL_SIZE.width * ratio, REAL_SIZE.height * ratio);
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
                MoveEnum.STOP, new Dimension(1, 2), false, map);
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

    @Override
    public void tick() {
        state = view.check(position.x, position.y + PHYSICS_SIZE.height);
        if (state == CollisionState.GAS) {
            velocity.y += GRAVITY;
            velocity.y *= 0.98;
        }
        switch (movement) {
            case RIGHT:
                velocity.x = SPEED;
                break;
            case LEFT:
                velocity.x = -SPEED;
                break;
            case STOP:
                velocity.x = 0;
                break;
        }
        int x;
        int slide = 0;
        if (velocity.x >= 0) {
            while (slide < velocity.x) {
                x = position.x + slide + PHYSICS_SIZE.width;
                topSideCollision = view.check(x, position.y);
                bottomSideCollision = view.check(x, position.y + PHYSICS_SIZE.height - 1);
                if (topSideCollision == CollisionState.SOLID
                        || bottomSideCollision == CollisionState.SOLID) {
                    velocity.x = 0;
                    break;
                }
                slide++;
            }
        } else {
            while (slide > velocity.x) {
                x = position.x + slide - 1;
                topSideCollision = view.check(x, position.y);
                bottomSideCollision = view.check(x, position.y + PHYSICS_SIZE.height - 1);
                if (topSideCollision == CollisionState.SOLID
                        || bottomSideCollision == CollisionState.SOLID) {
                    velocity.x = 0;
                    break;
                }
                slide--;
            }
        }
        position.x += slide;
        int y;
        int fall = 0;
        if (velocity.y >= 0) {
            while (fall < velocity.y) {
                y = position.y + fall + PHYSICS_SIZE.height;
                leftVerticalCollision = view.check(position.x, y);
                rightVerticalCollision = view.check(position.x + PHYSICS_SIZE.width - 1, y);
                if (leftVerticalCollision == CollisionState.SOLID
                        || rightVerticalCollision == CollisionState.SOLID) {
                    velocity.y = 0;
                    break;
                }
                fall++;
            }
        } else {
            while (fall > velocity.y) {
                y = position.y + fall - 1;
                leftVerticalCollision = view.check(position.x, y);
                rightVerticalCollision = view.check(position.x + PHYSICS_SIZE.width - 1, y);
                if (leftVerticalCollision == CollisionState.SOLID
                        || rightVerticalCollision == CollisionState.SOLID) {
                    velocity.y = 0;
                    break;
                }
                fall--;
            }
        }
        position.y += fall;
    }

    public void control(MoveEnum action) {
        switch (action) {
            case RIGHT:
            case LEFT:
            case STOP:
                movement = action;
                break;
            case JUMP:
                jump = true;
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
                VIEW_SIZE.width, VIEW_SIZE.height);
    }

    @Override
    public void drawRelative(Graphics2D g, AffineTransform trans) {
        AffineTransform tr = (AffineTransform) trans.clone();
        tr.translate(position.x, position.y);
//        tr.rotate(rotation);
        g.setTransform(tr);
        g.setColor(Color.RED);
        g.fillRect(0, 0, VIEW_SIZE.width, VIEW_SIZE.height);
//        g.drawImage(animation.getSprite(), null, null);
    }

    public SerializableBody serialize() {
        return new SerializableBody(
                new Point.Double(position.x / Main.RATIO, position.y / Main.RATIO),
                velocity, movement, REAL_SIZE, jump);
    }
}

package objects;

import main.Application;
import objects.items.InventoryTableModel;
import objects.items.ItemBlueprint;
import utilities.AbstractView;
import utilities.CollisionState;
import utilities.SendableVia;
import utilities.communication.SerializableBody;
import utilities.spritesheets.Animation;
import utilities.spritesheets.Frame;
import utilities.spritesheets.SpriteLoader;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.util.ArrayList;

/**
 * @author Václav Blažej
 */
public class Body implements GraphicComponent, SendableVia<Body, SerializableBody> {

    private static final double JUMP = 20;
    private static final double GRAVITY = 2;
    private static final int SPEED = 12;
    private static final int INITIAL_HEALTH = 100;
    private Point position;
    private Point.Double velocity;
    private MoveEnum movement;
    private Dimension physicsSize;
    private Dimension viewSize;
    private Dimension bodyBlockSize;
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

    public Body(Point position,
                Point.Double velocity,
                MoveEnum movement,
                Dimension bodyBlockSize,
                boolean jump,
                AbstractView view) {
        this.position = position;
        this.velocity = velocity;
        this.movement = movement;
        this.bodyBlockSize = bodyBlockSize;
        this.physicsSize = new Dimension(bodyBlockSize.width * Application.BLOCK_SIZE, bodyBlockSize.height * Application.BLOCK_SIZE);
        int ratio = view.getRatio();
        this.viewSize = new Dimension(bodyBlockSize.width * ratio, bodyBlockSize.height * ratio);
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
        this(new Point(x, y), new Point.Double(0, 0), MoveEnum.STOP, new Dimension(1, 2), false, map);
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
        state = view.check(position.x / Application.BLOCK_SIZE, (position.y + physicsSize.height) / Application.BLOCK_SIZE);
        if (state == CollisionState.GAS) {
            velocity.y += GRAVITY;
            velocity.y *= 0.95;
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

        int slide = 0;
        final int top = position.y / Application.BLOCK_SIZE;
        final int bottom = (position.y + physicsSize.height - 1) / Application.BLOCK_SIZE;
        if (velocity.x >= 0) {
            while (slide < velocity.x) {
                int x = (position.x + physicsSize.width + slide) / Application.BLOCK_SIZE;
                topSideCollision = view.check(x, top);
                bottomSideCollision = view.check(x, bottom);
                if (topSideCollision == CollisionState.SOLID || bottomSideCollision == CollisionState.SOLID) {
                    velocity.x = 0;
                    break;
                }
                slide++;
            }
        } else {
            while (slide > velocity.x) {
                int x = (position.x - 1 - slide) / Application.BLOCK_SIZE;
                topSideCollision = view.check(x, top);
                bottomSideCollision = view.check(x, bottom);
                if (topSideCollision == CollisionState.SOLID || bottomSideCollision == CollisionState.SOLID) {
                    velocity.x = 0;
                    break;
                }
                slide--;
            }
        }
        position.x += slide;

        int fall = 0;
        final int left = position.x / Application.BLOCK_SIZE;
        final int right = (position.x + physicsSize.width - 1) / Application.BLOCK_SIZE;
        if (velocity.y >= 0) {
            while (fall < velocity.y) {
                int y = (position.y + physicsSize.height + fall) / Application.BLOCK_SIZE;
                leftVerticalCollision = view.check(left, y);
                rightVerticalCollision = view.check(right, y);
                if (leftVerticalCollision == CollisionState.SOLID || rightVerticalCollision == CollisionState.SOLID) {
                    velocity.y = 0;
                    break;
                }
                fall++;
            }
        } else {
            while (fall > velocity.y) {
                int y = (position.y - 1 - fall) / Application.BLOCK_SIZE;
                leftVerticalCollision = view.check(left, y);
                rightVerticalCollision = view.check(right, y);
                if (leftVerticalCollision == CollisionState.SOLID || rightVerticalCollision == CollisionState.SOLID) {
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
        g.fillRect(position.x / Application.BLOCK_SIZE, position.y / Application.BLOCK_SIZE,
                bodyBlockSize.width, bodyBlockSize.height);
    }

    @Override
    public void drawRelative(Graphics2D g, AffineTransform trans) {
        AffineTransform tr = new AffineTransform(trans);
        tr.translate(position.x, position.y);
//        tr.rotate(rotation);
        g.setTransform(tr);
        g.setColor(Color.RED);
        g.fillRect(0, 0, viewSize.width, viewSize.height);

//        final int top = position.y / Application.BLOCK_SIZE;
//        final int bottom = (position.y + physicsSize.height - 1) / Application.BLOCK_SIZE;
//        int x = (position.x + physicsSize.width) / Application.BLOCK_SIZE;

//        g.drawImage(animation.getSprite(), null, null);
    }

    @Override
    public SerializableBody serialize() {
        return new SerializableBody(position, velocity, movement, bodyBlockSize, jump);
    }
}

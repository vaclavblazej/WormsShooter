package cz.spacks.worms.model.objects;

import cz.spacks.worms.Application;
import cz.spacks.worms.model.objects.items.InventoryTableModel;
import cz.spacks.worms.model.objects.items.ItemBlueprint;
import cz.spacks.worms.controller.AbstractView;
import cz.spacks.worms.controller.CollisionState;
import cz.spacks.worms.controller.SendableVia;
import cz.spacks.worms.controller.communication.SerializableBody;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;

/**
 *
 */
public class Body implements GraphicComponent, SendableVia<Body, SerializableBody> {

    private static final double JUMP_FORCE = 24;
    private static final double GRAVITY_FORCE = 4;
    private static final int GROUND_SPEED = 12;
    private static final int LIQUID_SPEED = 8;
    private static final int INITIAL_HEALTH = 100;
    private Point position;
    private Point.Double velocity;
    private MoveEnum horizontalMovement;
    private MoveEnum verticalMovement;
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
//    private Frame frame;

    public Body(Point position,
                Point.Double velocity,
                MoveEnum horizontalMovement,
                MoveEnum verticalMovement,
                Dimension bodyBlockSize,
                boolean jump,
                AbstractView view) {
        this.position = position;
        this.velocity = velocity;
        this.horizontalMovement = horizontalMovement;
        this.verticalMovement = verticalMovement;
        this.bodyBlockSize = bodyBlockSize;
        this.physicsSize = new Dimension(bodyBlockSize.width * Application.BLOCK_SIZE, bodyBlockSize.height * Application.BLOCK_SIZE);
        int ratio = view.getRatio();
        this.viewSize = new Dimension(bodyBlockSize.width * ratio, bodyBlockSize.height * ratio);
        this.jump = jump;
        this.view = view;
        this.inventory = new InventoryTableModel("Item", "Count");
        this.alive = false;
//        SpriteLoader.loadSprite("player");
//        SpriteLoader.set(32, 64);
//        frame = SpriteLoader.getSprite(0, 0);
    }

    public Body(int x, int y, AbstractView map) {
        this(new Point(x, y), new Point.Double(0, 0), MoveEnum.HSTOP, MoveEnum.VSTOP, new Dimension(1, 2), false, map);
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
        int speed;
        switch (state) {
            case GAS:
                velocity.y += GRAVITY_FORCE;
                velocity.y *= 0.95;
                speed = GROUND_SPEED;
                break;
            case LIQUID:
                speed = LIQUID_SPEED;
                break;
            default:
                speed = GROUND_SPEED;
        }
        // horizontal movement
        switch (horizontalMovement) {
            case RIGHT:
                velocity.x = (velocity.x + speed) / 2;
                break;
            case LEFT:
                velocity.x = (velocity.x - speed) / 2;
                break;
            case HSTOP:
                velocity.x = Math.signum(velocity.x) * Math.max(Math.abs(velocity.x) - 1, 0) / 2;
                break;
        }
        switch (state){
            case LIQUID:
                switch (verticalMovement) {
                    case UP:
                        velocity.y = (velocity.y - speed) / 2;
                        break;
                    case DOWN:
                        velocity.y = (velocity.y + speed) / 2;
                        break;
                    case VSTOP:
                        velocity.y = Math.signum(velocity.y) * Math.max(Math.abs(velocity.y) - 1, 0) / 2;
                        break;
                }
                break;
        }

        // exact horizontal collision
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
                int x = (position.x - 1 + slide) / Application.BLOCK_SIZE;
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

        // exact vertical collision
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
                    jump = true;
                    break;
                }
                fall++;
            }
        } else {
            while (fall > velocity.y) {
                int y = (position.y - 1 + fall) / Application.BLOCK_SIZE;
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
            case HSTOP:
                horizontalMovement = action;
                break;
            case UP:
            case DOWN:
            case VSTOP:
                verticalMovement = action;
                break;
            case JUMP:
                if (jump) {
                    velocity.y -= JUMP_FORCE;
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
        g.setColor(Color.BLUE);
        g.fillRect(0, 0, viewSize.width, viewSize.height);
        g.setColor(Color.RED);
        g.drawRect(0, 0, viewSize.width, viewSize.height);

//        final int top = position.y / Application.BLOCK_SIZE;
//        final int bottom = (position.y + physicsSize.height - 1) / Application.BLOCK_SIZE;
//        int x = (position.x + physicsSize.width) / Application.BLOCK_SIZE;

//        g.drawImage(frame.getFrame(), null, null);
    }

    @Override
    public SerializableBody serialize() {
        return new SerializableBody(position, velocity, horizontalMovement, verticalMovement, bodyBlockSize, jump);
    }
}

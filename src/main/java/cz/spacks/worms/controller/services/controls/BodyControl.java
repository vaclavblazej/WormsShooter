package cz.spacks.worms.controller.services.controls;

import cz.spacks.worms.controller.Settings;
import cz.spacks.worms.controller.properties.CollisionState;
import cz.spacks.worms.controller.services.WorldService;
import cz.spacks.worms.controller.services.logics.movement.MovementLogic;
import cz.spacks.worms.model.objects.Body;
import cz.spacks.worms.model.objects.MoveEnum;

import java.awt.*;
import java.awt.geom.Point2D;

public class BodyControl {

    private static final double JUMP_FORCE = 24;
    private static final double GRAVITY_FORCE = 4;
    private static final int GROUND_SPEED = 12;
    private static final int LIQUID_SPEED = 8;

    private MoveEnum horizontalMovement;
    private MoveEnum verticalMovement;
    private boolean jump;
    private Dimension physicsSize;
    private CollisionState leftVerticalCollision;
    private CollisionState rightVerticalCollision;
    private CollisionState topSideCollision;
    private CollisionState bottomSideCollision;

    private MovementLogic movementLogic = MovementLogic.NULL;
    private Body body;

    public BodyControl(Body body) {
        this.body = body;
        final Dimension bodyBlockSize = body.getBodyBlockSize();
        this.physicsSize = new Dimension(bodyBlockSize.width * Settings.BLOCK_SIZE, bodyBlockSize.height * Settings.BLOCK_SIZE);
        this.horizontalMovement = MoveEnum.HSTOP;
        this.verticalMovement = MoveEnum.VSTOP;
        this.jump = false;
    }

    public void control(MoveEnum action) {
        final Point2D.Double velocity = body.getVelocity();
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

    public MoveEnum getHorizontalMovement() {
        return horizontalMovement;
    }

    public MoveEnum getVerticalMovement() {
        return verticalMovement;
    }

    public void tick(WorldService worldService) {
        final Point position = body.getPosition();
        final Point2D.Double velocity = body.getVelocity();
        CollisionState state = worldService.getState(position.x / Settings.BLOCK_SIZE, (position.y + physicsSize.height) / Settings.BLOCK_SIZE);
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
        switch (state) {
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
        final int top = position.y / Settings.BLOCK_SIZE;
        final int bottom = (position.y + physicsSize.height - 1) / Settings.BLOCK_SIZE;
        if (velocity.x >= 0) {
            while (slide < velocity.x) {
                int x = (position.x + physicsSize.width + slide) / Settings.BLOCK_SIZE;
                topSideCollision = worldService.getState(x, top);
                bottomSideCollision = worldService.getState(x, bottom);
                if (topSideCollision == CollisionState.SOLID || bottomSideCollision == CollisionState.SOLID) {
                    velocity.x = 0;
                    break;
                }
                slide++;
            }
        } else {
            while (slide > velocity.x) {
                int x = (position.x - 1 + slide) / Settings.BLOCK_SIZE;
                topSideCollision = worldService.getState(x, top);
                bottomSideCollision = worldService.getState(x, bottom);
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
        final int left = position.x / Settings.BLOCK_SIZE;
        final int right = (position.x + physicsSize.width - 1) / Settings.BLOCK_SIZE;
        if (velocity.y >= 0) {
            while (fall < velocity.y) {
                int y = (position.y + physicsSize.height + fall) / Settings.BLOCK_SIZE;
                leftVerticalCollision = worldService.getState(left, y);
                rightVerticalCollision = worldService.getState(right, y);
                if (leftVerticalCollision == CollisionState.SOLID || rightVerticalCollision == CollisionState.SOLID) {
                    velocity.y = 0;
                    jump = true;
                    break;
                }
                fall++;
            }
        } else {
            while (fall > velocity.y) {
                int y = (position.y - 1 + fall) / Settings.BLOCK_SIZE;
                leftVerticalCollision = worldService.getState(left, y);
                rightVerticalCollision = worldService.getState(right, y);
                if (leftVerticalCollision == CollisionState.SOLID || rightVerticalCollision == CollisionState.SOLID) {
                    velocity.y = 0;
                    break;
                }
                fall--;
            }
        }
        position.y += fall;
    }

    public Body getBody() {
        return body;
    }

    public void setBody(Body body) {
        this.body = body;
    }
}

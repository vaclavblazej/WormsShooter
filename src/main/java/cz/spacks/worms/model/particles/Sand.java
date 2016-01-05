package cz.spacks.worms.model.particles;

import java.awt.*;

/**
 *
 */
public class Sand extends Particle {

    public Point.Double velocity;

    public Sand(int x, int y, double vx, double vy, Color color) {
        super(x, y, color);
        this.velocity = new Point.Double(vx, vy);
    }

    public void tick() {
        /*while (MainPanel.check((int) position.x, (int) position.y) != BACKGROUND) {
         position.y -= 1;
         if (clear()) {
         MainPanel.destroy(this);
         return;
         }
         }*/
        /*if (MainPanel.check((int) position.x, (int) position.y + 1) != CollisionState.Free) {
         boolean left = MainPanel.check((int) position.x - 1, (int) position.y) == CollisionState.Free;
         boolean right = MainPanel.check((int) position.x + 1, (int) position.y) == CollisionState.Free;
         boolean leftD = MainPanel.check((int) position.x - 1, (int) position.y + 1) == CollisionState.Free;
         boolean rightD = MainPanel.check((int) position.x + 1, (int) position.y + 1) == CollisionState.Free;
         if (!left && !right) {
         MainPanel.imprint(this);
         } else if (left && leftD) {
         position.y += 1;
         position.x -= 1;
         } else if (right && rightD) {
         position.y += 1;
         position.x += 1;
         } else {
         MainPanel.imprint(this);
         return;
         }
         velocity.y = 0;
         }

         velocity.y += 0.1;

         int change = 0;
         double j = 0;
         int dirX = (velocity.x >= 0) ? 1 : -1;
         int dirY = (velocity.y >= 0) ? 1 : -1;
         double part = velocity.x / (velocity.y + 1);
         double whole = 1;
         double absX = Math.abs(velocity.x) + 1;
         double absY = Math.abs(velocity.y) + 1;
         for (int i = 0; i < absY; i++) {
         for (int k = 0; k < absX; k++) {
         if (MainPanel.check(
         (int) (position.x + k * dirX),
         (int) (position.y + i * dirY)) != CollisionState.Free) {
         if (k == 0) {
         position.x += k * dirX;
         position.y += i * dirY - dirY;
         } else {
         position.x += k * dirX - dirX;
         position.y += i * dirY;
         }
         velocity.x = 0;
         //velocity.y = 0;
         return;
         }
         }
         }
         position.x += velocity.x;
         position.y += velocity.y;*/
    }

    public void draw(Graphics g) {
        g.drawLine(
                (int) position.x,
                (int) position.y,
                (int) position.x,
                (int) position.y);
    }
}

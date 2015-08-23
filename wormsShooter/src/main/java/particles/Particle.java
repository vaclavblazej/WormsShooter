package particles;

import java.awt.*;

/**
 * @author Skarab
 */
public abstract class Particle {

    public static final Color BACKGROUND = Color.BLACK;
    public Point.Double position;
    public Color color;

    public Particle() {
        this(0, 0, Color.WHITE);
    }

    public Particle(int x, int y, Color color) {
        this.position = new Point.Double(x, y);
        this.color = color;
    }

    public abstract void tick();

    public abstract void draw(Graphics g);

    public boolean clear() {
        return false;//position.x < 0 || position.y < 0 || position.x > ClientView.SIZE.width || position.y > ClientView.SIZE.height;
    }
}

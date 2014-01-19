package particles;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import client.MainPanel;

/**
 *
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
        return position.x < 0 || position.y < 0 || position.x > MainPanel.SIZE.width || position.y > MainPanel.SIZE.height;
    }
}

package objects;

import java.awt.*;
import java.io.Serializable;

/**
 * @author Skarab
 */
public class LightSource implements Serializable {

    private static final long serialVersionUID = 1L;
    private Point position;
    private int strength;

    public LightSource(int x, int y, int strength) {
        this.position = new Point(x, y);
        this.strength = strength;
    }

    public LightSource(Point position, int strength) {
        this.position = position;
        this.strength = strength;
    }

    public Point getPosition() {
        return position;
    }

    public int getStrength() {
        return strength;
    }
}

package objects;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import main.Main;
import objects.items.itemActions.ItemAction;
import utilities.communication.SerializableBufferedImage;

/**
 *
 * @author Skarab
 */
public class Thing implements GraphicComponent {

    private Point size;
    private Point position;
    private SerializableBufferedImage image;
    private transient ItemAction action = null;
    private transient BufferedImage cache = null;

    public Thing(BufferedImage img, Point size, Point position, ItemAction action) {
        this.image = new SerializableBufferedImage(img);
        this.size = size;
        this.position = position;
        this.action = action;
    }

    public Thing(BufferedImage img, Point size, Point position) {
        this(img, size, position, null);
    }

    @Override
    public void draw(Graphics2D g) {
        g.setColor(Color.YELLOW);
        g.drawRect(position.x, position.y, size.x, size.y);
    }

    public ItemAction getAction() {
        return action;
    }

    @Override
    public void tick() {
    }

    @Override
    public void drawRelative(Graphics2D g, AffineTransform trans) {
        g.setTransform(trans);
        g.translate(position.x * Main.RATIO, position.y * Main.RATIO);
        if (cache == null) {
            cache = image.getImage();
        }
        g.drawImage(cache, null, null);
    }
}

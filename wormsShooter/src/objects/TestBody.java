package objects;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.EnumSet;
import utilities.MapInterface;
import utilities.communication.SerializableBody;

/**
 *
 * @author Skarab
 */
public class TestBody implements GraphicComponent {

    private Point.Double position;
    private Point.Double velocity;
    private EnumSet<ControlsEnum> controls;
    private Dimension SIZE;
    private Dimension REAL_SIZE;
    private int ratio;
    private boolean jump;
    private MapInterface map;

    public TestBody(Point2D.Double position, Point2D.Double velocity,
            EnumSet<ControlsEnum> controls, Dimension REAL_SIZE,
            boolean jump, MapInterface map) {
        this.position = position;
        this.velocity = velocity;
        this.controls = controls;
        this.REAL_SIZE = REAL_SIZE;
        this.ratio = map.getRatio();
        this.SIZE = new Dimension(REAL_SIZE.width * ratio, REAL_SIZE.height * ratio);
        this.jump = jump;
        this.map = map;
    }

    public TestBody(int x, int y, int ratio, MapInterface map) {
        position = new Point.Double(x, y);
        velocity = new Point.Double(0, 0);
        this.ratio = ratio;
        this.map = map;
        controls = EnumSet.noneOf(ControlsEnum.class);
        REAL_SIZE = new Dimension(1, 2);
        SIZE = new Dimension(REAL_SIZE.width * ratio, REAL_SIZE.height * ratio);
        jump = false;
    }

    public void setPosition(int x, int y) {
        this.position.x = x;
        this.position.y = y;
    }

    public Point getPosition() {
        return new Point((int) position.x, (int) position.y);
    }

    public Point2D.Double getVelocity() {
        return velocity;
    }

    public void setVelocity(double x, double y) {
        this.velocity.x = x;
        this.velocity.y = y;
    }

    public void tick() {
        double differenceY = velocity.y;
        int directionY = (differenceY >= 0) ? 1 : -1;
        double absoluteY = Math.abs(differenceY);
        boolean next = true;
        velocity.y += 0.1;
        for (int i = 1; next && i <= absoluteY; i++) {
            switch (map.check((int) position.x, (int) position.y + REAL_SIZE.height - 1 + directionY)) {
                case Free:
                    position.y += directionY;
                    jump = false;
                    break;
                case Crushed:
                    jump = true;
                    next = false;
                    velocity.y = 0;
                    break;
            }
        }
        if (map.check((int) position.x + 1, (int) position.y + 1) == CollisionState.Free) {
            if (controls.contains(ControlsEnum.Right)) {
                position.x += 1;
            }
        }
        if (map.check((int) position.x - 1, (int) position.y + 1) == CollisionState.Free) {
            if (controls.contains(ControlsEnum.Left)) {
                position.x -= 1;
            }
        }
    }

    public void controlOn(ControlsEnum en) {
        if (controls.add(en)) {
            switch (en) {
                case Right:
                    break;
                case Left:
                    break;
                case Up:
                    if (jump == true) {
                        velocity.y -= 2.4;
                        jump = false;
                    }
                    break;
                case Down:
                    break;
            }
        }
    }

    public void controlOff(ControlsEnum en) {
        if (controls.remove(en)) {
            switch (en) {
                case Right:
                case Left:
                    break;
                case Up:
                case Down:
                    break;
            }
        }
    }

    @Override
    public void draw(Graphics2D g) {
        //AffineTransform transformer = new AffineTransform();
        //transformer.translate(position.x, position.y);
        g.setColor(Color.RED);
        g.fillRect((int) position.x, (int) position.y, SIZE.width, SIZE.height);
    }

    public void drawRelative(Graphics2D g) {
        g.setColor(Color.RED);
        g.fillRect(0, 0, SIZE.width, SIZE.height);
    }

    public SerializableBody serialize() {
        return new SerializableBody(position, velocity, controls, REAL_SIZE, jump);
    }
}

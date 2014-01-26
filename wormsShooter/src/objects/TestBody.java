package objects;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.EnumSet;
import static objects.CollisionState.Free;
import utilities.MapInterface;
import utilities.communication.SerializableBody;

/**
 *
 * @author Skarab
 */
public class TestBody implements GraphicComponent {

    private static final double JUMP = 0.6;
    private Point.Double position;
    private Point.Double velocity;
    private EnumSet<ControlsEnum> controls;
    private Dimension SIZE;
    private Dimension REAL_SIZE;
    private int ratio;
    private boolean jump;
    private MapInterface map;
    private Inventory inventory;

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
        this.inventory = new Inventory();
    }

    public TestBody(int x, int y, MapInterface map) {
        this(new Point.Double(x, y), new Point.Double(0, 0),
                EnumSet.noneOf(ControlsEnum.class),
                new Dimension(1, 2), false, map);
    }
    
    public Inventory getInventory(){
        return inventory;
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

    public void fallBy(double y) {
        int directionY = (velocity.y >= 0) ? 1 : -1;
        double absoluteY = Math.abs(y) + 0.00001;
        int i;
        for (i = 1; i <= absoluteY; i++) {
            if (map.check((int) position.x, (int) position.y + REAL_SIZE.height + i * directionY)
                    == CollisionState.Crushed) {
                velocity.y = 0;
                break;
            }
        }
        position.y += i * directionY;
    }

    public void tick() {
        int directionY = (velocity.y >= 0) ? 1 : -1;
        switch (map.check((int) position.x, (int) position.y + REAL_SIZE.height - 1 + directionY)) {
            case Free:
                velocity.y += 0.1;
                fallBy(velocity.y);
                break;
            case Crushed:
                velocity.y = 0;
                jump = true;
                break;
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
                        velocity.y -= JUMP;
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

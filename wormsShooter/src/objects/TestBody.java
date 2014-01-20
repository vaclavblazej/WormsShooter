package objects;

import client.MainPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.EnumSet;
import utilities.MapInterface;

/**
 *
 * @author Skarab
 */
public class TestBody implements GraphicComponent {

    private Point position;
    private EnumSet<ControlsEnum> controls;
    private Dimension SIZE;
    private Dimension REAL_SIZE;
    private int ratio;
    private MapInterface map;

    public TestBody(int x, int y, int ratio, MapInterface map) {
        position = new Point(x, y);
        this.ratio = ratio;
        this.map = map;
        controls = EnumSet.noneOf(ControlsEnum.class);
        REAL_SIZE = new Dimension(1, 2);
        SIZE = new Dimension(REAL_SIZE.width * ratio, REAL_SIZE.height * ratio);
    }

    public void setPosition(Point position) {
        this.position = position;
    }

    public Point getPosition() {
        return position;
    }

    public void tick() {
        int c = 0;
        if (controls.contains(ControlsEnum.Up)) {
            c = 1;
        }
        switch (map.check(position.x, position.y + REAL_SIZE.height + c)) {
            case Free:
                position.y += 1;
                break;
        }
        if (map.check(position.x + 1, position.y + 1) == CollisionState.Free) {
            if (controls.contains(ControlsEnum.Right)) {
                position.x += 1;
            }
        }
        if (map.check(position.x - 1, position.y + 1) == CollisionState.Free) {
            if (controls.contains(ControlsEnum.Left)) {
                position.x -= 1;
            }
        }
        if (controls.contains(ControlsEnum.Down)) {
            position.y += 1;
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
                    position.y -= 1;
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
        g.fillRect(position.x, position.y, SIZE.width, SIZE.height);
    }

    public void drawRelative(Graphics2D g) {
        g.setColor(Color.RED);
        g.fillRect(0, 0, SIZE.width, SIZE.height);
    }
}

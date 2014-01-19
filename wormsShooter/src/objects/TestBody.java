package objects;

import client.MainPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.util.EnumSet;

/**
 *
 * @author Skarab
 */
public class TestBody implements GraphicComponent {

    private Point position;
    private EnumSet<ControlsEnum> controls;
    private Dimension SIZE;

    public TestBody(int x, int y) {
        position = new Point(x, y);
        controls = EnumSet.noneOf(ControlsEnum.class);
        SIZE = new Dimension(14, 30);
    }

    public Point getPosition() {
        return position;
    }

    public void tick() {
        if (MainPanel.check(position.x, position.y + SIZE.height + 1) == MainPanel.BACKGROUND) {
            position.y += 1;
        } /*else if (MainPanel.check(position.x, position.y + SIZE.height) != MainPanel.BACKGROUND) {
            position.y -= 1;
        }*/
        if (controls.contains(ControlsEnum.Left)) {
            position.x -= 1;
        }
        if (controls.contains(ControlsEnum.Right)) {
            position.x += 1;
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
}

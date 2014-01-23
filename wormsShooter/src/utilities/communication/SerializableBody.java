package utilities.communication;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.io.Serializable;
import java.util.EnumSet;
import objects.ControlsEnum;
import objects.TestBody;
import utilities.MapInterface;

/**
 *
 * @author Skarab
 */
public class SerializableBody implements Serializable {

    private Point.Double position;
    private Point.Double velocity;
    private EnumSet<ControlsEnum> controls;
    private Dimension REAL_SIZE;
    private boolean jump;

    public SerializableBody(Point2D.Double position, Point2D.Double velocity,
            EnumSet<ControlsEnum> controls, Dimension REAL_SIZE,
            boolean jump) {
        this.position = position;
        this.velocity = velocity;
        this.controls = controls;
        this.REAL_SIZE = REAL_SIZE;
        this.jump = jump;
    }

    public TestBody deserialize(MapInterface map) {
        return new TestBody(position, velocity, controls, REAL_SIZE, jump, map);
    }
}

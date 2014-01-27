package objects;

import utilities.ControlsEnum;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Skarab
 */
public class Body implements GraphicComponent {

    private List<Part> parts;
    private AffineTransform transform;

    public Body(int x, int y) {
        transform = new AffineTransform();
        parts = new ArrayList<>();
    }
    
    public void addPart(Part part){
        parts.add(part);
    }

    public void tick() {
    }

    public void controlOn(ControlsEnum en) {
    }

    public void controlOff(ControlsEnum en) {
    }

    @Override
    public void draw(Graphics2D g) {
        for (Part part : parts) {
            part.draw(g);
        }
    }
}

package wormsshooter;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.util.ArrayList;
import java.util.EnumSet;
import spritesheets.Animation;
import spritesheets.ControlsEnum;
import static spritesheets.ControlsEnum.Down;
import static spritesheets.ControlsEnum.Up;
import spritesheets.Frame;
import spritesheets.Sprite;

/**
 *
 * @author Skarab
 */
public class Worm implements GraphicComponent {

    private static final Point HEAD_POSITION = new Point(9, -17);
    private static final Point HEAD_CENTER = new Point(8, 16);
    private static final Point BODY_CENTER = new Point(10, 10);
    private BufferedImage img;
    private Point position;
    private ArrayList<Frame> images;
    private Animation animation;
    private Frame headFrame;
    private double headRotation;
    private double headChange;
    private EnumSet<ControlsEnum> controls;

    public Worm(int x, int y) {
        headRotation = 0;
        position = new Point(x, y);
        images = new ArrayList<>(5);
        Sprite.set(19, 24);
        Sprite.loadSprite("WormHead");
        headFrame = Sprite.getSprite(0, 0);
        // head
        Sprite.set(21, 17);
        Sprite.loadSprite("WormBody");
        images.add(Sprite.getSprite(0, 0, 0));
        images.add(Sprite.getSprite(0, 1, 0));
        images.add(Sprite.getSprite(0, 2, 2));
        images.add(Sprite.getSprite(0, 3, 2));

        animation = new Animation(images, 4);

        controls = EnumSet.noneOf(ControlsEnum.class);
    }

    public void tick() {
        int tmp = animation.value();;
        if (animation.update()) {
            if (animation.getDirection() > 0) {
                position.x += tmp;
            } else {
                position.x += -animation.value();
            }
        }
        headRotation += headChange;
        if (headRotation > Math.PI / 2) {
            headRotation = Math.PI / 2;
        } else if (headRotation < -Math.PI / 2) {
            headRotation = -Math.PI / 2;
        }
    }

    public void controlOn(ControlsEnum en) {
        if (controls.add(en)) {
            switch (en) {
                case Right:
                    animation.setDirection(1);
                    animation.start();
                    break;
                case Left:
                    animation.setDirection(-1);
                    animation.start();
                    break;
                case Up:
                    headChange = -0.1;
                    break;
                case Down:
                    headChange = 0.1;
                    break;
            }
        }
    }

    public void controlOff(ControlsEnum en) {
        if (controls.remove(en)) {
            switch (en) {
                case Right:
                case Left:
                    animation.stop();
                    break;
                case Up:
                case Down:
                    headChange = 0;
                    break;
            }
        }
    }

    @Override
    public void draw(Graphics2D g, ImageObserver obs) {
        AffineTransform transformer = new AffineTransform();
        transformer.translate(position.x, position.y);
        g.drawImage(animation.getSprite(), transformer, null);

        //HEAD_POSITION
        //transformer.translate(HEAD_TRANSFORM.x, HEAD_TRANSFORM.y);
        transformer.translate(HEAD_POSITION.x, HEAD_POSITION.y);
        transformer.translate(HEAD_CENTER.x, HEAD_CENTER.y);
        transformer.rotate(headRotation);
        transformer.translate(-HEAD_CENTER.x, -HEAD_CENTER.y);
        g.drawImage(headFrame.getFrame(), transformer, null);
        //g.drawImage(img, null, null);
    }
}

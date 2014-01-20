package objects;

import client.MainPanel;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.EnumSet;
import static objects.ControlsEnum.Down;
import static objects.ControlsEnum.Up;
import spritesheets.Animation;
import spritesheets.Frame;
import spritesheets.SpriteLoader;

/**
 *
 * @author Skarab
 */
public class Worm implements GraphicComponent {

    private static final Point HEAD_POSITION = new Point(9, -17);
    private static final Point HEAD_CENTER = new Point(8, 16);
    private static final Point BODY_CENTER = new Point(10, 10);
    private static final int LASER_LENGTH = 500;
    private static final int HEIGHT = 8;
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
        SpriteLoader.set(19, 24);
        SpriteLoader.loadSprite("WormHead");
        headFrame = SpriteLoader.getSprite(0, 0);
        // head
        SpriteLoader.set(21, 17);
        SpriteLoader.loadSprite("WormBody");
        images.add(SpriteLoader.getSprite(0, 0, 0));
        images.add(SpriteLoader.getSprite(0, 1, 0));
        images.add(SpriteLoader.getSprite(0, 2, 2));
        images.add(SpriteLoader.getSprite(0, 3, 2));

        animation = new Animation(images, 4);

        controls = EnumSet.noneOf(ControlsEnum.class);
    }

    public void tick() {
        /*if (MainPanel.check(position.x, position.y + HEIGHT + 1) == CollisionState.Free) {
            position.y += 1;
        } else if (MainPanel.check(position.x, position.y + HEIGHT) != CollisionState.Free) {
            position.y -= 1;
        }
        int tmp = animation.value();
        if (animation.update()) {
            if (animation.getDirection() > 0) {
                position.x += tmp;
            } else {
                position.x += -tmp;
            }
        }
        headRotation += headChange;
        if (headRotation > Math.PI / 2) {
            headRotation = Math.PI / 2;
        } else if (headRotation < -Math.PI / 2) {
            headRotation = -Math.PI / 2;
        }*/
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
                case Fire:
                    //MainPanel.newSand(position.x, position.y);
                    break;
            }
        }
    }

    @Override
    public void draw(Graphics2D g) {
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
        g.setColor(Color.RED);
        g.drawLine(position.x, position.y,
                position.x + (int) (LASER_LENGTH * Math.cos(headRotation)),
                position.y + (int) (LASER_LENGTH * Math.sin(headRotation)));
        //g.drawImage(img, null, null);
    }
}

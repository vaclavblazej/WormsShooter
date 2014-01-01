package wormsshooter;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.util.ArrayList;
import spritesheets.Animation;
import spritesheets.Sprite;

/**
 *
 * @author Skarab
 */
public class Worm implements GraphicComponent {

    BufferedImage img;
    private Point position;
    private ArrayList<BufferedImage> images;
    private Animation animation;

    public Worm(int x, int y) {
        position = new Point(x, y);
        images = new ArrayList<>();
        Sprite.set(50);
        Sprite.loadSprite("Worm");
        images.add(Sprite.getSprite(0, 0));
        images.add(Sprite.getSprite(0, 1));
        images.add(Sprite.getSprite(0, 2));

        animation = new Animation(images, 10);
        animation.start();

        /*Frame frames = Sprite.;
         anim = new Animation(frames, frameDelay);*/
        /*URL url = Main.class.getResource("/images/Grass.bmp");
         try {
         img = ImageIO.read(url);
         } catch (IOException ex) {
         Logger.getLogger(Worm.class.getName()).log(Level.SEVERE, null, ex);
         }*/
    }

    public void tick() {
        animation.update();
    }

    @Override
    public void draw(Graphics2D g, ImageObserver obs) {
        g.drawImage(animation.getSprite(), new AffineTransform(1, 0, 0, 1, position.x, position.y), null);
        //g.drawImage(img, null, null);
    }
}

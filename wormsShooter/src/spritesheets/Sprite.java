package spritesheets;

/**
 *
 * @author Skarab
 */
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import wormsshooter.Main;
import wormsshooter.Worm;

public class Sprite {

    private static BufferedImage spriteSheet;
    private static int tileSize = 32;
    private static int x = 0;
    private static int y = 0;

    public static void set(int tileSize) {
        set(tileSize, 0, 0);
        Sprite.tileSize = tileSize;
    }

    public static void set(int tileSize, int x, int y) {
        Sprite.tileSize = tileSize;
        Sprite.x = x;
        Sprite.y = y;
    }

    public static BufferedImage loadSprite(String file) {
        BufferedImage sprite = null;
        /*
         URL url = Main.class.getResource("/images/Grass.bmp");
         try {
         img = ImageIO.read(url);
         } catch (IOException ex) {
         Logger.getLogger(Worm.class.getName()).log(Level.SEVERE, null, ex);
         }
         */
        //try {
            URL url = Main.class.getResource("/images/" + file + ".png");
            try {
                sprite = ImageIO.read(url);
            } catch (IOException ex) {
                Logger.getLogger(Worm.class.getName()).log(Level.SEVERE, null, ex);
            }
/*            sprite = ImageIO.read(new File("/images/" + file + ".bmp"));
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        spriteSheet = sprite;
        return sprite;
    }

    public static BufferedImage getSprite(int xGrid, int yGrid) {
        if (spriteSheet == null) {
            return null;
        }
        return spriteSheet.getSubimage(x + xGrid * tileSize, y + yGrid * tileSize, tileSize, tileSize);
    }

    private Sprite() {
    }
}
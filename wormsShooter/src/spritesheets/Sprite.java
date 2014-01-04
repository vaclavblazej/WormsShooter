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
    private static int tileSizeX = 32;
    private static int tileSizeY = 32;
    private static int x = 0;
    private static int y = 0;

    public static void set(int tileSizeX, int tileSizeY) {
        set(tileSizeX, tileSizeY, 0, 0);
    }

    public static void set(int tileSizeX, int tileSizeY, int x, int y) {
        Sprite.tileSizeX = tileSizeX;
        Sprite.tileSizeY = tileSizeY;
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

    public static Frame getSprite(int xGrid, int yGrid) {
        return getSprite(xGrid, yGrid, 0);
    }

    public static Frame getSprite(int xGrid, int yGrid, int val) {
        if (spriteSheet == null) {
            return null;
        }
        return new Frame(spriteSheet.getSubimage(x + xGrid * tileSizeX, y + yGrid * tileSizeY, tileSizeX, tileSizeY), 1, val);
    }

    private Sprite() {
    }
}
package spritesheets;

/**
 *
 * @author Skarab
 */
import client.GameWindow;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import main.Main;
import objects.Worm;

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
        URL url = Main.class.getResource("/images/" + file + ".png");
        try {
            sprite = ImageIO.read(url);
        } catch (Exception ex) {
            //GameWindow.getInstance().showError("Could not load: " + url.toString());
            Logger.getLogger(Worm.class.getName()).log(Level.SEVERE, null, ex);
        }
        spriteSheet = sprite;
        return sprite;
    }

    public static Frame getSprite() {
        return getSprite(0, 0, 0);
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
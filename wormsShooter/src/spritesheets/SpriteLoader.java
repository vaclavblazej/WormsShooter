package spritesheets;

/**
 *
 * @author Skarab
 */
import client.GameWindow;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;
import main.Main;
import utilities.Message;

public class SpriteLoader {

    private static BufferedImage spriteSheet;
    private static int tileSizeX = 32;
    private static int tileSizeY = 32;
    private static int x = 0;
    private static int y = 0;

    public static void set(int tileSizeX, int tileSizeY) {
        set(tileSizeX, tileSizeY, 0, 0);
    }

    public static void set(int tileSizeX, int tileSizeY, int x, int y) {
        SpriteLoader.tileSizeX = tileSizeX;
        SpriteLoader.tileSizeY = tileSizeY;
        SpriteLoader.x = x;
        SpriteLoader.y = y;
    }

    public static BufferedImage loadSprite(String file) {
        BufferedImage sprite = null;
        URL url = Main.class.getResource(Message.Image_folder.cm() + file + Message.Image_format.cm());
        try {
            sprite = ImageIO.read(url);
        } catch (IllegalArgumentException | IOException ex) {
            //Logger.getLogger(Worm.class.getName()).log(Level.SEVERE, null, ex);
            GameWindow.getInstance().showError(new Exception(
                    Message.Image_load_error.cm()
                    + Message.Image_folder.cm()
                    + file
                    + Message.Image_format.cm()));
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

    private SpriteLoader() {
    }
}
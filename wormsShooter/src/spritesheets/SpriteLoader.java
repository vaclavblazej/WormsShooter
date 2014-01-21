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
import sun.util.BuddhistCalendar;
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
        URL localUrl = Main.class.getResource(Message.Image_folder.cm() + file + Message.Image_format.cm());
        try {
            // read image from local source
            sprite = ImageIO.read(localUrl);
        } catch (IllegalArgumentException | IOException e) {
//            try {
            // read image from online source
//                URL serverUrl = new URL(Message.Image_online_folder.cm() + file + Message.Image_format.cm());
//                sprite = ImageIO.read(serverUrl);
//                File outputfile = new File(Message.Image_save_folder.cm() + file + Message.Image_format.cm());
//                ImageIO.write(sprite, "png", outputfile);
//            } catch (IllegalArgumentException | IOException ex) {
            GameWindow.getInstance().showError(new Exception(
                    Message.Image_load_error.cm()
                    + Message.Image_folder.cm()
                    + file
                    + Message.Image_format.cm()));
//            }
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

    public static BufferedImage getRawSprite() {
        return getRawSprite(0, 0, 0);
    }

    public static BufferedImage getRawSprite(int xGrid, int yGrid) {
        return getRawSprite(xGrid, yGrid, 0);
    }

    public static BufferedImage getRawSprite(int xGrid, int yGrid, int val) {
        if (spriteSheet == null) {
            return null;
        }
        return spriteSheet.getSubimage(x + xGrid * tileSizeX, y + yGrid * tileSizeY, tileSizeX, tileSizeY);
    }

    private SpriteLoader() {
    }
}
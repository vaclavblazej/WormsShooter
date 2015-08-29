package utilities.spritesheets;

/**
 * @author Václav Blažej
 */

import client.ClientWindow;
import utilities.properties.Message;
import utilities.properties.Paths;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.logging.Logger;

public class SpriteLoader {

    private static final Logger logger = Logger.getLogger(SpriteLoader.class.getName());

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
        return loadSprite(file, Paths.IMAGE_FORMAT.value());
    }

    public static BufferedImage loadSprite(String file, String format) {
        BufferedImage sprite = null;
        final String path = Paths.IMAGE_FOLDER.value() + file + format;
        URL localUrl = SpriteLoader.class.getResource(path);
        try {
            // read image from local source
            sprite = ImageIO.read(localUrl);
        } catch (IllegalArgumentException | IOException e) {
            try {
                logger.info("Downloading " + file + Paths.IMAGE_FORMAT.value());
                //read image from online source
                URL serverUrl = new URL(Paths.IMAGE_ONLINE_FOLDER.value() + file + Paths.IMAGE_FORMAT.value());
                sprite = ImageIO.read(serverUrl);
                saveSprite(file, sprite);
            } catch (IllegalArgumentException | IOException ex) {
                ClientWindow.getInstance().showError(new Exception(Message.IMAGE_LOAD_ERROR.value() + path));
            }
        }
        spriteSheet = sprite;
        return sprite;
    }

    public static boolean saveSprite(String file, BufferedImage image) {
        String path = Paths.IMAGE_SAVE_FOLDER.value() + file + Paths.IMAGE_FORMAT.value();
        try {
            File outFile = new File(path);
            final boolean newFile = outFile.createNewFile();
            if (!newFile) throw new RuntimeException("cannot create file " + path);
            ImageIO.write(image, "png", outFile);
            return true;
        } catch (IllegalArgumentException | IOException e) {
            throw new RuntimeException(e);
//            return false;
        }
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
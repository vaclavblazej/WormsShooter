package utilities.spritesheets;

/**
 * @author Skarab
 */

import client.ClientWindow;
import main.Application;
import utilities.properties.Message;
import utilities.properties.Paths;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

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
        URL localUrl = Application.class.getResource(Paths.IMAGE_FOLDER.cm() + file + Paths.IMAGE_FORMAT.cm());
        try {
            // read image from local source
            sprite = ImageIO.read(localUrl);
        } catch (IllegalArgumentException | IOException e) {
            try {
                System.out.println("Downloading " + file + Paths.IMAGE_FORMAT.cm());
                //read image from online source
                URL serverUrl = new URL(Paths.IMAGE_ONLINE_FOLDER.cm() + file + Paths.IMAGE_FORMAT.cm());
                sprite = ImageIO.read(serverUrl);
                saveSprite(file, sprite);
            } catch (IllegalArgumentException | IOException ex) {
                ClientWindow.getInstance().showError(new Exception(
                        Message.IMAGE_LOAD_ERROR.cm()
                                + Paths.IMAGE_FOLDER.cm()
                                + file
                                + Paths.IMAGE_FORMAT.cm()));
            }
        }
        spriteSheet = sprite;
        return sprite;
    }

    public static boolean saveSprite(String file, BufferedImage image) {
        String path = Paths.IMAGE_SAVE_FOLDER.cm() + file + Paths.IMAGE_FORMAT.cm();
        try {
            File outputfile = new File(path);
            outputfile.createNewFile();
            ImageIO.write(image, "png", outputfile);
            return true;
        } catch (IllegalArgumentException | IOException e) {
            return false;
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
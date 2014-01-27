package utilities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;
import spritesheets.SpriteLoader;

/**
 *
 * @author Skarab
 */
public enum Materials {

    AIR,
    WOOD,
    SAND,
    DIRT,
    GRASS,
    STONE;
    private static final Map<Integer, Material> material;
    private static final Map<Materials, Color> codes;
    public static final CollisionState DEFAULT = CollisionState.SOLID;

    static {
        material = new HashMap<>();
        codes = new HashMap<>();
        SpriteLoader.loadSprite("Materials");
        SpriteLoader.set(20, 20);
        addMaterial(AIR, "#84AAF8", CollisionState.FREE);
        addMaterial(WOOD, "#976B4B", CollisionState.FREE, 2, 0);
        addMaterial(DIRT, "#D3C66F", CollisionState.SOLID, 8, 2);
        addMaterial(GRASS, "#964316", CollisionState.SOLID, 7, 2);
        addMaterial(STONE, "#1CD85E", CollisionState.SOLID, 0, 3);
    }

    private static void addMaterial(Materials mat, String code, CollisionState state) {
        Color color = Color.decode(code);
        codes.put(mat, color);
        material.put(color.getRGB(), new Material(null, state));
    }

    private static void addMaterial(Materials mat, String code, CollisionState state, int x, int y) {
        Color color = Color.decode(code);
        codes.put(mat, color);
        material.put(color.getRGB(), new Material(SpriteLoader.getRawSprite(x, y), state));
    }

    public static CollisionState check(Color color) {
        Material result = material.get(color.getRGB());
        return (result != null) ? result.state : DEFAULT;
    }

    public Color getColor() {
        return codes.get(this);
    }

    private static BufferedImage getImage(int rgb) {
        if (material.containsKey(rgb)) {
            return material.get(rgb).image;
        }
        return null;
    }

    public static void redraw(BufferedImage source, BufferedImage dest) {
        Graphics g = dest.getGraphics();
        g.drawImage(source, 0, 0, dest.getWidth(), dest.getHeight(), null);
        int w = source.getWidth();
        int h = source.getHeight();
        int ratio = dest.getWidth() / w;
        BufferedImage b;
        for (int j = 0; j < h; j++) {
            for (int i = 0; i < w; i++) {
                b = getImage(source.getRGB(i, j));
                if (b != null) {
                    g.drawImage(b, i * ratio, j * ratio, b.getWidth(), b.getHeight(), null);
                }
            }
        }
    }

    private Materials() {
    }

    private static class Material {

        public BufferedImage image;
        public CollisionState state;

        Material(BufferedImage image, CollisionState state) {
            this.image = image;
            this.state = state;
        }
    }
}

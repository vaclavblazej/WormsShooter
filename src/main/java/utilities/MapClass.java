package utilities;

import objects.LightSource;
import utilities.communication.SerializableMapClass;
import utilities.materials.Material;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.logging.Logger;

/**
 * @author Václav Blažej
 */
public class MapClass implements SendableVia<MapClass, SerializableMapClass> {

    private static final Logger logger = Logger.getLogger(MapClass.class.getName());

    private BufferedImage map;
    private int[][] shadows;
    private AbstractView view;
    private ArrayList<LightSource> lights;

    public MapClass(BufferedImage map, AbstractView view) {
        this(map, view, null);
    }

    public MapClass(BufferedImage map, AbstractView view, ArrayList<LightSource> lights) {
        this(map, view, lights, new int[map.getWidth()][map.getHeight()]);
    }

    private MapClass(BufferedImage map, AbstractView view, ArrayList<LightSource> lights, int[][] shadows) {
        this.map = map;
        this.view = view;
        this.shadows = shadows;
        this.lights = (lights != null) ? lights : new ArrayList<>();
    }

    public int getWidth() {
        return map.getWidth();
    }

    public int getHeight() {
        return map.getHeight();
    }

    public Graphics getGraphics() {
        return map.getGraphics();
    }

    public Integer getRGB(int x, int y) {
        return map.getRGB(x, y);
    }

    public Integer getShadow(int x, int y) {
        return shadows[x][y];
    }

    public MapClass getSubmap(Point offset, Dimension size) throws ArrayIndexOutOfBoundsException {
        int[][] ns = new int[size.width][size.height];
        for (int[] s : ns) Arrays.fill(s, 0x00); // todo
//        for (int j = 0; j < size.height; j++) {
//            for (int i = 0; i < size.width; i++) {
//                ns[i][j] = shadows[offset.x + i][offset.y + j];
//            }
//        }
        final int width = map.getWidth();
        final int height = map.getHeight();
        if(offset.x < 0) throw new ArrayIndexOutOfBoundsException("You tried to get subimage with left x index = " + offset.x);
        if(offset.y < 0) throw new ArrayIndexOutOfBoundsException("You tried to get subimage with top y index = " + offset.y);
        if(offset.x + size.width > width) throw new ArrayIndexOutOfBoundsException("You tried to get subimage with right x index = " + (offset.x + size.width));
        if(offset.y + size.height > height) throw new ArrayIndexOutOfBoundsException("You tried to get subimage with bottom y index = " + (offset.y + size.height));
        final BufferedImage subimage = map.getSubimage(offset.x, offset.y, size.width, size.height);
        return new MapClass(subimage, view, lights, ns);
    }

    public void addLightSource(LightSource source) {
        lights.add(source);
        logger.info("added light source");
    }

    public void calculateShadows(Material material) {
//        Arrays.fill(shadows, 0xFF);
        recalculateShadows(material);
    }

    public void recalculateShadows(Point p) {
        LinkedList<Point> samples = new LinkedList<>();
        samples.add(new Point(p.x, p.y + 1));
        samples.add(new Point(p.x, p.y - 1));
        samples.add(new Point(p.x + 1, p.y));
        samples.add(new Point(p.x - 1, p.y));
        recalculateShadows(samples);
    }

    public void recalculateShadows(Material material) {
        LinkedList<Point> samples = new LinkedList<>();
        /*for (int i = 0; i < width; i++) {
         for (int j = 0; j < height; j++) {
         if (material.getMaterial(map.getRGB(i, j)).equals(MaterialEnum.AIR)) {
         Point sampleLight = new Point(i, j);
         samples.add(sampleLight);
         shadows[sampleLight.x][sampleLight.y] = 0;
         }
         }
         }*/
        logger.info("recalculation with " + lights.size() + " sources");
        for (LightSource source : lights) {
            Point sampleLight = source.getPosition();
            samples.add(sampleLight);
            shadows[sampleLight.x][sampleLight.y] = 0;
        }
        recalculateShadows(samples);
    }

    public void recalculateShadows(LinkedList<Point> samples) {
        if (samples == null) {
            samples = new LinkedList<>();
        }
        Point work;
        while (!samples.isEmpty()) {
            work = samples.removeFirst();
            try {
                int sh = shadows[work.x][work.y];
                procShadow(work.x, work.y + 1, samples, sh);
                procShadow(work.x, work.y - 1, samples, sh);
                procShadow(work.x + 1, work.y, samples, sh);
                procShadow(work.x - 1, work.y, samples, sh);
            } catch (ArrayIndexOutOfBoundsException ignored) {
            }
        }
    }

    private void procShadow(int x, int y, LinkedList<Point> samples, double sh) {
        Material mat = view.getMaterial();
        CollisionState state = mat.getState(mat.getMaterial(map.getRGB(x, y)));
        sh += mat.getTransparency(mat.getMaterial(map.getRGB(x, y)));
        if (sh > 0xFF) sh = 0xFF;
        if (shadows[x][y] > (int) sh) {
            shadows[x][y] = (int) sh;
            samples.add(new Point(x, y));
        }
    }

    public BufferedImage getImage() {
        return map;
    }

    public BufferedImage getSubimage(int x, int y, int width, int height) {
        return map.getSubimage(x, y, width, height);
    }

    @Override
    public SerializableMapClass serialize() {
        return new SerializableMapClass(map, lights);
    }
}

package utilities;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.LinkedList;
import objects.LightSource;
import utilities.communication.SerializableMapClass;
import utilities.materials.Material;

/**
 *
 * @author Skarab
 */
public class MapClass {

    private BufferedImage map;
    private int[][] shadows;
    private AbstractView view;
    private int width;
    private int height;
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
        width = map.getWidth();
        height = map.getHeight();
        this.shadows = shadows;
        if (lights != null) {
            this.lights = lights;
        } else {
            this.lights = new ArrayList<>(10);
        }
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

    public MapClass getSubmap(int x, int y, int width, int height)
            throws ArrayIndexOutOfBoundsException {
        int[][] ns = new int[width][height];
        for (int j = 0; j < height; j++) {
            for (int i = 0; i < width; i++) {
                ns[i][j] = shadows[x + i][y + j];
            }
        }
        return new MapClass(map.getSubimage(x, y, width, height), view, lights, ns);
    }

    public void addLightSource(LightSource source) {
        lights.add(source);
        System.out.println("add light source");
    }

    public void calculateShadows(Material material) {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                shadows[i][j] = 0xFF;
            }
        }
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
        System.out.println("recalculation woth " + lights.size() + " sources");
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
            } catch (ArrayIndexOutOfBoundsException ex) {
                continue;
            }
        }
    }

    private void procShadow(int x, int y, LinkedList<Point> samples, double sh) {
        Material mat = view.getMaterial();
        CollisionState state = mat.getState(mat.getMaterial(map.getRGB(x, y)));
        sh += mat.getTransparency(mat.getMaterial(map.getRGB(x, y)));
        if (sh > 0xFF) {
            sh = 0xFF;
        }
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

    public SerializableMapClass serialize() {
        return new SerializableMapClass(map, lights);
    }
}

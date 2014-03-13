package utilities;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.LinkedList;
import objects.LightSource;
import utilities.communication.SerializableMapClass;
import utilities.materials.Material;
import utilities.materials.MaterialEnum;

/**
 *
 * @author Skarab
 */
public class MapClass {

    private MaterialEnum[][] map;
    private BufferedImage cacheMap = null;
    private int[][] shadows;
    private AbstractView view;
    private int width;
    private int height;
    private ArrayList<LightSource> lights;

    public MapClass(MaterialEnum[][] map, AbstractView view) {
        this(map, view, null);
    }

    public MapClass(MaterialEnum[][] map, AbstractView view, ArrayList<LightSource> lights) {
        this(map, view, lights, new int[map.length][map[0].length]);
    }

    private MapClass(MaterialEnum[][] map, AbstractView view, ArrayList<LightSource> lights, int[][] shadows) {
        this.map = map;
        this.view = view;
        width = map.length;
        height = map[0].length;
        this.shadows = shadows;
        if (lights != null) {
            this.lights = lights;
        } else {
            this.lights = new ArrayList<>(10);
        }
    }

    public int getWidth() {
        return map.length;
    }

    public int getHeight() {
        return map[0].length;
    }

    public MaterialEnum getBlock(int x, int y) {
        return map[x][y];
    }

    public void setBlock(int x, int y, MaterialEnum mat) {
        map[x][y] = mat;
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
        return new MapClass(getSubimage(x, y, width, height), view, lights, ns);
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
        CollisionState state = mat.getState(map[x][y]);
        sh += map[x][y].getOpacity().getValue();
        if (sh > 0xFF) {
            sh = 0xFF;
        }
        if (shadows[x][y] > (int) sh) {
            shadows[x][y] = (int) sh;
            samples.add(new Point(x, y));
        }
    }

    public BufferedImage getData() {
        if (cacheMap == null) {
            cacheMap = new BufferedImage(map.length, map[0].length, BufferedImage.TYPE_INT_BGR);
            for (int i = 0; i < map.length; i++) {
                for (int j = 0; j < map[0].length; j++) {
                    System.out.println(i + " " + j +" " + map[i][j]);
                    cacheMap.setRGB(i, j, map[i][j].getColor().getRGB());
                }
            }
        }
        return cacheMap;
    }

    public MaterialEnum[][] getSubimage(int x, int y, int width, int height) {
        MaterialEnum[][] subimage = new MaterialEnum[width][height];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                subimage[i][j] = map[x + i][y + j];
            }
        }
        return subimage;
    }

    public SerializableMapClass serialize() {
        return new SerializableMapClass(map, lights);
    }
}

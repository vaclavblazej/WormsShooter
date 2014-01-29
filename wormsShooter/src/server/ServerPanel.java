package server;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;
import javax.swing.AbstractAction;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import objects.GraphicComponent;
import objects.TestBody;
import objects.items.Crafting;
import objects.items.ItemBlueprint;
import objects.items.ItemEnum;
import objects.items.ItemFactory;
import objects.items.Recipe;
import particles.Particle;
import particles.Sand;
import spritesheets.SpriteLoader;
import utilities.CollisionState;
import utilities.MapInterface;
import utilities.Material;
import utilities.communication.Model;

/**
 *
 * @author Skarab
 */
public class ServerPanel extends JPanel implements MapInterface, ActionListener {

    public static final Dimension SIZE = new Dimension(400, 300);
    private static ServerPanel instance;
    private static final int RNG = 20;
    private static final int RATIO = 1;

    public static ServerPanel getInstance() {
        if (instance == null) {
            instance = new ServerPanel();
        }
        return instance;
    }
    private BufferedImage map;
    private CopyOnWriteArrayList<Particle> grains;
    private CopyOnWriteArrayList<GraphicComponent> objects;
    private Random random;
    private Timer tickTimer;
    private Timer graphicTimer;
    private List<TestBody> bodies;
    private Model model;
    private ItemFactory itemFactory;

    private ServerPanel() {
        createItems();
        bodies = new ArrayList<>(10);
        SpriteLoader.loadSprite("Map");
        SpriteLoader.set(400, 300);
        map = SpriteLoader.getSprite().getFrame();
        grains = new CopyOnWriteArrayList<>();
        objects = new CopyOnWriteArrayList<>();
        random = new Random();
        model = new Model(map,
                ServerCommunication.getInstance().getControls(),
                itemFactory,
                ServerComService.getInstance().getCounter());
        createReceipes();

        setFocusable(true);
        setPreferredSize(SIZE);
        tickTimer = new Timer(40, this);
        graphicTimer = new Timer(100, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                repaint();
            }
        });
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                init();
            }
        });
    }

    private void createItems() {
        itemFactory = new ItemFactory();
        itemFactory.addItem(ItemEnum.METAL, new ItemBlueprint("Metal", 1, 1));
        itemFactory.addItem(ItemEnum.GUN_POWDER, new ItemBlueprint("Gun powder", 1, 1));
        itemFactory.addItem(ItemEnum.BULLET, new ItemBlueprint("Bullet", 1, 1));
        itemFactory.addItem(ItemEnum.HANDGUN, new ItemBlueprint("Gun", 2, 2));
        itemFactory.addItem(ItemEnum.HANDGUN_MAGAZINE, new ItemBlueprint("Magazine", 1, 2));
        itemFactory.addItem(ItemEnum.HANDGUN_BARREL, new ItemBlueprint("Barrel", 2, 1));
        itemFactory.addItem(ItemEnum.HANDGUN_MECHANISM, new ItemBlueprint("Mechanism", 1, 1));
        itemFactory.addItem(ItemEnum.HANDGUN_HANDLE, new ItemBlueprint("Handle", 2, 2));
        itemFactory.addItem(ItemEnum.HANDGUN_OPTICS, new ItemBlueprint("Optics", 2, 1));
    }

    public ItemFactory getItemFactory() {
        return itemFactory;
    }

    private void createReceipes() {
        ItemFactory fac = getModel().getFactory();
        Crafting rec = fac.getRecipes();
        Recipe r = new Recipe("Gun assemble");
        r.addIngredient(fac.getBlueprint(ItemEnum.HANDGUN_BARREL), 1);
        r.addIngredient(fac.getBlueprint(ItemEnum.HANDGUN_HANDLE), 1);
        r.addIngredient(fac.getBlueprint(ItemEnum.HANDGUN_MECHANISM), 1);
        r.addProduct(fac.getBlueprint(ItemEnum.HANDGUN), 1);
        rec.addReceipe(r);
        r = new Recipe("Gun disassemble");
        r.addIngredient(fac.getBlueprint(ItemEnum.HANDGUN), 1);
        r.addProduct(fac.getBlueprint(ItemEnum.HANDGUN_BARREL), 1);
        r.addProduct(fac.getBlueprint(ItemEnum.HANDGUN_HANDLE), 1);
        r.addProduct(fac.getBlueprint(ItemEnum.HANDGUN_MECHANISM), 1);
        rec.addReceipe(r);
        r = new Recipe("Gun barrel");
        r.addIngredient(fac.getBlueprint(ItemEnum.METAL), 4);
        r.addProduct(fac.getBlueprint(ItemEnum.HANDGUN_BARREL), 1);
        rec.addReceipe(r);
        r = new Recipe("Gun handle");
        r.addIngredient(fac.getBlueprint(ItemEnum.METAL), 8);
        r.addProduct(fac.getBlueprint(ItemEnum.HANDGUN_HANDLE), 1);
        rec.addReceipe(r);
        r = new Recipe("Gun mechanism");
        r.addIngredient(fac.getBlueprint(ItemEnum.METAL), 4);
        r.addProduct(fac.getBlueprint(ItemEnum.HANDGUN_MECHANISM), 1);
        rec.addReceipe(r);
        r = new Recipe("Bullet");
        r.addIngredient(fac.getBlueprint(ItemEnum.METAL), 1);
        r.addIngredient(fac.getBlueprint(ItemEnum.GUN_POWDER), 1);
        r.addProduct(fac.getBlueprint(ItemEnum.BULLET), 1);
        rec.addReceipe(r);
    }

    public void save() {
        SpriteLoader.saveSprite("Map", map);
    }

    public void change(int x, int y, Material mat) {
        Graphics g = map.getGraphics();
        g.setColor(mat.getColor());
        g.drawLine(x, y, x, y);
    }

    public Model getModel() {
        return model;
    }

    public void setModel(Model model) {
        this.model = model;
    }

    public TestBody newBody() {
        TestBody b = new TestBody(100, 100, this);
        bodies.add(b);
        return b;
    }

    public CollisionState check(int x, int y) {
        return Material.check(getPixel(x, y));
    }

    public Color getPixel(int x, int y) {
        try {
            return new Color(map.getRGB(x, y));
        } catch (ArrayIndexOutOfBoundsException ex) {
            return Color.BLACK;
        }
    }

    public void imprint(Particle gr) {
        destroy(gr);
        Graphics g = map.getGraphics();
        g.setColor(gr.color);
        gr.draw(g);
    }

    public void imprint(int x, int y, Color color) {
        Graphics g = map.getGraphics();
        g.setColor(color);
        g.drawLine(x, y, x, y);
    }

    private void erase(int x, int y, int r) {
        for (int i = 0; i < r; i++) {
            for (int j = 0; j < r; j++) {
                imprint(x + i, y + j, Color.BLACK);
            }
        }
    }

    public void destroy(Particle gr) {
        grains.remove(gr);
    }

    public void update(int x, int y) {
    }

    private void addObject(GraphicComponent comp) {
        objects.add(comp);
    }

    public BufferedImage getMap() {
        ColorModel cm = map.getColorModel();
        boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
        WritableRaster raster = map.copyData(null);
        return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
    }

    public void newSand(int x, int y) {
        grains.add(new Sand(
                x + random.nextInt(10) - 5,
                y + random.nextInt(20) - 10,
                (random.nextInt(RNG) - RNG / 2) / 10.,
                -(random.nextInt(RNG) - RNG / 2) / 10.,
                Color.CYAN));
    }

    public void init() {
        tickTimer.start();
        graphicTimer.start();
    }

    @Override
    public void paint(Graphics grphcs) {
        super.paint(grphcs);
        Graphics2D g = (Graphics2D) grphcs;
        g.drawImage(map, 0, 0, getWidth(), getHeight(), null);
        for (TestBody b : bodies) {
            b.draw(g);
        }
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        for (TestBody b : bodies) {
            b.tick();
        }
        for (Particle particle : grains) {
            particle.tick();
        }
    }

    @Override
    public int getRatio() {
        return RATIO;
    }
}

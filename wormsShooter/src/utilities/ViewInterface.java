package utilities;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;
import javax.swing.JPanel;
import javax.swing.Timer;
import objects.GraphicComponent;
import objects.TestBody;
import objects.items.Crafting;
import objects.items.ItemFactory;
import particles.Particle;
import particles.Sand;
import utilities.communication.Model;
import utilities.materials.Material;
import utilities.materials.MaterialEnum;

/**
 *
 * @author Skarab
 */
public abstract class ViewInterface extends JPanel implements ActionListener {

    private static final int RNG = 20;
    protected Model model;
    protected BufferedImage map;
    protected Collection<TestBody> bodies;
    protected static CopyOnWriteArrayList<Particle> grains;
    protected CopyOnWriteArrayList<GraphicComponent> objects;
    protected Random random;
    protected Timer tickTimer;
    private final int ratio;
    private final Dimension size;

    public ViewInterface(int width, int height, int ratio) {
        this.ratio = ratio;
        this.size = new Dimension(width, height);
        tickTimer = new Timer(40, this);
        bodies = new ArrayList<>(10);
        random = new Random();
        grains = new CopyOnWriteArrayList<>();
        objects = new CopyOnWriteArrayList<>();
        setPreferredSize(size);
        setFocusable(true);
    }

    public CollisionState check(int x, int y) {
        return Material.check(getPixel(x, y));
    }

    public Model getModel() {
        return model;
    }

    public void setModel(Model model) {
        this.model = model;
    }

    public ItemFactory getItemFactory() {
        return getModel().getFactory();
    }

    public Crafting getRecipes() {
        return getModel().getFactory().getRecipes();
    }

    public void init() {
        tickTimer.start();
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        for (TestBody b : bodies) {
            b.tick();
        }
        repaint();
    }

    public MaterialEnum change(int x, int y, MaterialEnum mat) {
        Graphics g = map.getGraphics();
        MaterialEnum ret = Material.getMaterial(map.getRGB(x, y));
        g.setColor(mat.getColor());
        g.drawLine(x, y, x, y);
        return ret;
    }

    public TestBody newBody() {
        TestBody b = new TestBody(100, 100, this);
        bodies.add(b);
        return b;
    }

    private void addObject(GraphicComponent comp) {
        objects.add(comp);
    }

    @Override
    public void paint(Graphics grphcs) {
        super.paint(grphcs);
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

    public void swap(int x, int y, int sx, int sy) {
        Color first = getPixel(x, y);
        Color second = getPixel(sx, sy);
        imprint(x, y, second);
        imprint(sx, sy, first);
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

    public void newSand(int x, int y) {
        grains.add(new Sand(
                x + random.nextInt(10) - 5,
                y + random.nextInt(20) - 10,
                (random.nextInt(RNG) - RNG / 2) / 10.,
                -(random.nextInt(RNG) - RNG / 2) / 10.,
                Color.CYAN));
    }

    public int getRatio() {
        return ratio;
    }

    public Dimension getSize() {
        return size;
    }
}

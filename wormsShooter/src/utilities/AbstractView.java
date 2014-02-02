package utilities;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;
import javax.swing.Timer;
import objects.Miscellaneous;
import objects.TestBody;
import objects.items.Crafting;
import objects.items.ItemFactory;
import particles.Particle;
import utilities.communication.Model;
import utilities.materials.Material;
import utilities.materials.MaterialEnum;

/**
 *
 * @author Skarab
 */
public abstract class AbstractView extends JPanel implements ActionListener {

    private static final int RNG = 20;
    protected Model model;
    protected MapClass map;
    protected Collection<TestBody> bodies;
    protected List<Miscellaneous> objects;
    protected Random random;
    protected Timer tickTimer;
    private final int ratio;
    private final Dimension size;
    private boolean check;

    public AbstractView(int width, int height, int ratio) {
        this.ratio = ratio;
        this.size = new Dimension(width, height);
        tickTimer = new Timer(40, this);
        random = new Random();
        setPreferredSize(size);
        setFocusable(true);
        check = false;
        reset();
        if (!check) {
            Logger.getLogger(AbstractView.class.getName())
                    .log(Level.SEVERE, null, new Exception(
                    "Every " + AbstractView.class.getSimpleName()
                    + " should call super.reset() in its reset!"));
        }
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

    public void reset() {
        check = true;
        bodies = new ArrayList<>(10);
        objects = new ArrayList<>(10);
        tickTimer.stop();
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
        TestBody b = new TestBody(100, 200, this);
        bodies.add(b);
        return b;
    }

    public void removeBody(TestBody b) {
        bodies.remove(b);
    }

    public void addObject(Miscellaneous comp) {
        objects.add(comp);
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
        //grains.remove(gr);
    }

    public void update(int x, int y) {
    }

    public void newSand(int x, int y) {
        /*grains.add(new Sand(
         x + random.nextInt(10) - 5,
         y + random.nextInt(20) - 10,
         (random.nextInt(RNG) - RNG / 2) / 10.,
         -(random.nextInt(RNG) - RNG / 2) / 10.,
         Color.CYAN));*/
    }

    public int getRatio() {
        return ratio;
    }

    public Dimension getSize() {
        return size;
    }
}

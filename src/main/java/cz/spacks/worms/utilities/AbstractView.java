package cz.spacks.worms.utilities;

import cz.spacks.worms.objects.Body;
import cz.spacks.worms.objects.GraphicComponent;
import cz.spacks.worms.objects.items.Crafting;
import cz.spacks.worms.objects.items.ItemFactory;
import cz.spacks.worms.particles.Particle;
import cz.spacks.worms.utilities.communication.Model;
import cz.spacks.worms.utilities.defaults.DefaultComponentListener;
import cz.spacks.worms.utilities.materials.Material;
import cz.spacks.worms.utilities.materials.MaterialEnum;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Represents general view on the scene.
 *
 * @author V�clav Bla�ej
 */
public abstract class AbstractView extends JPanel implements ActionListener, DefaultComponentListener {

    private static final Logger logger = Logger.getLogger(AbstractView.class.getName());

    protected Model model;
    protected Material material;
    protected MapClass map;
    protected List<Body> bodies;
    protected List<GraphicComponent> objects;
    protected Random random;
    protected Timer tickTimer;
    private final int ratio;
    private boolean check;

    /**
     * Creates view and calls reset method which is obligatory to implement.
     *
     * @param ratio
     */
    public AbstractView(int ratio) {
        this.ratio = ratio;
        tickTimer = new Timer(40, this);
        random = new Random();
        check = false;
        reset();
        if (!check) {
            logger.log(Level.SEVERE, null, new Exception(
                    "Every " + AbstractView.class.getSimpleName() + " should call super.reset() in its reset!"));
        }
    }

    public CollisionState check(int x, int y) {
        if (material != null) {
            return material.getState(getPixel(x, y));
        } else {
            return Material.DEFAULT;
        }
    }

    public Model getModel() {
        return model;
    }

    public void setModel(Model model) {
        this.model = model;
        material = new Material(this);
        map = model.getMap();
        bodies = new ArrayList<>(model.getControls().values());
    }

    public Material getMaterial() {
        return material;
    }

    public ItemFactory getItemFactory() {
        return getModel().getFactory();
    }

    public Crafting getRecipes() {
        return getModel().getFactory().getRecipes();
    }

    public void init() {
        componentResized(new ComponentEvent(this, 0));
        tickTimer.start();
    }

    public void reset() {
        check = true;
        bodies = new ArrayList<>(10);
        objects = new CopyOnWriteArrayList<>();
        tickTimer.stop();
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        for (Body b : bodies) {
            b.tick();
        }
        for (GraphicComponent g : objects) {
            g.tick();
        }
        repaint();
    }

    public MaterialEnum change(int x, int y, MaterialEnum mat) {
        Graphics g = map.getGraphics();
        MaterialEnum ret = material.getMaterial(map.getRGB(x, y));
        g.setColor(getMaterial().getColor(mat));
        g.drawLine(x, y, x, y);
        return ret;
    }

    public Body newBody() {
        Body b = new Body(2000, 1600, this);
        bodies.add(b);
        return b;
    }

    public void addBody(Body body) {
        bodies.add(body);
    }

    public void removeBody(Body b) {
        bodies.remove(b);
    }

    public void addObject(GraphicComponent comp) {
        objects.add(comp);
    }

    public void removeObject(GraphicComponent comp) {
        objects.remove(comp);
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
}
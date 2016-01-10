package cz.spacks.worms.controller;

import cz.spacks.worms.controller.materials.MaterialModel;
import cz.spacks.worms.controller.properties.CollisionState;
import cz.spacks.worms.model.MapModel;
import cz.spacks.worms.model.objects.Body;
import cz.spacks.worms.model.objects.GraphicComponent;
import cz.spacks.worms.model.objects.Crafting;
import cz.spacks.worms.model.objects.items.ItemFactory;
import cz.spacks.worms.model.objects.Model;
import cz.spacks.worms.controller.defaults.DefaultComponentListener;
import cz.spacks.worms.controller.materials.MaterialEnum;

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
 *
 */
public abstract class AbstractView extends JPanel implements ActionListener, DefaultComponentListener {

    private static final Logger logger = Logger.getLogger(AbstractView.class.getName());

    protected Model model;
    protected MaterialModel materialModel;
    protected MapModel map;
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
        if (materialModel != null) {
            return materialModel.getState(getPixel(x, y));
        } else {
            return MaterialModel.DEFAULT;
        }
    }

    public Model getModel() {
        return model;
    }

    public void setModel(Model model) {
        this.model = model;
        materialModel = new MaterialModel(this);
        map = model.getMap();
        bodies = new ArrayList<>(model.getControls().values());
    }

    public MaterialModel getMaterialModel() {
        return materialModel;
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
            b.tick(this);
        }
        for (GraphicComponent g : objects) {
            g.tick(this);
        }
        repaint();
    }

    public MaterialEnum change(int x, int y, MaterialEnum mat) {
        Graphics g = map.getGraphics();
        MaterialEnum ret = materialModel.getMaterial(map.getRGB(x, y));
        g.setColor(getMaterialModel().getColor(mat));
        g.drawLine(x, y, x, y);
        return ret;
    }

    public Body newBody() {
        Body b = new Body(2000, 1600);
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

    public int getRatio() {
        return ratio;
    }
}

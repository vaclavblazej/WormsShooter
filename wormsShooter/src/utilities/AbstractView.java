package utilities;

import client.ChatLog;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;
import javax.swing.Timer;
import main.Main;
import objects.Body;
import objects.GraphicComponent;
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
    protected Material material;
    protected MapClass map;
    protected List<Body> bodies;
    protected List<GraphicComponent> objects;
    protected Random random;
    protected Timer tickTimer;
    protected ChatLog chatLog;
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
        chatLog = new ChatLog();
    }

    public CollisionState check(int x, int y) {
        if (material != null) {
            return material.getState(getPixel((int) (x / Main.RATIO), (int) (y / Main.RATIO)));
        } else {
            return Material.DEFAULT;
        }
    }

    public Model getModel() {
        return model;
    }

    public void setModel(Model model) {
        this.model = model;
        material = new Material(model.getFactory());
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
    
    public void logChat(String string){
        chatLog.log(string);
    }

    public void init() {
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
        MaterialEnum ret = map.getBlock(x, y);
        map.setBlock(x,y,mat);
        return ret;
    }

    public Body newBody() {
        Body b = new Body(100, 200, this);
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

    public MaterialEnum getPixel(int x, int y) {
        try {
            return map.getBlock(x, y);
        } catch (ArrayIndexOutOfBoundsException ex) {
            return MaterialEnum.STONE;
        }
    }

    /*public void imprint(Particle gr) {
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
        grains.add(new Sand(
         x + random.nextInt(10) - 5,
         y + random.nextInt(20) - 10,
         (random.nextInt(RNG) - RNG / 2) / 10.,
         -(random.nextInt(RNG) - RNG / 2) / 10.,
         Color.CYAN));
    }*/

    public int getRatio() {
        return ratio;
    }

    public Dimension getSize() {
        return size;
    }
}

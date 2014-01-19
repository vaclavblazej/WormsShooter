package server;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;
import javax.swing.AbstractAction;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import objects.GraphicComponent;
import objects.Worm;
import particles.Particle;
import particles.Sand;
import spritesheets.Sprite;

/**
 *
 * @author Skarab
 */
public class ServerPanel extends JPanel implements ActionListener {

    private static final int RNG = 20;
    public static final Dimension SIZE = new Dimension(400, 300);
    private static BufferedImage map;
    private static CopyOnWriteArrayList<Particle> grains;
    private static CopyOnWriteArrayList<GraphicComponent> objects;
    private static Random random;

    static {
        Sprite.loadSprite("Map");
        Sprite.set(400, 300);
        map = Sprite.getSprite().getFrame();
        //map = new BufferedImage(SIZE.width, SIZE.height, BufferedImage.TYPE_INT_RGB);
        grains = new CopyOnWriteArrayList<>();
        objects = new CopyOnWriteArrayList<>();
        random = new Random();
    }

    public static int check(int x, int y) {
        int rgb;
        try {
            rgb = map.getRGB(x, y);
        } catch (ArrayIndexOutOfBoundsException ex) {
            return Color.BLACK.getRGB();
        }
        return rgb;
    }

    public static void imprint(Particle gr) {
        destroy(gr);
        Graphics g = map.getGraphics();
        g.setColor(gr.color);
        gr.draw(g);
    }

    public static void imprint(int x, int y, Color color) {
        Graphics g = map.getGraphics();
        g.setColor(color);
        g.drawLine(x, y, x, y);
    }

    public static void swap(int x, int y, int sx, int sy) {
        int first = check(x, y);
        int second = check(sx, sy);
        imprint(x, y, Color.getColor(Integer.toString(second)));
        imprint(sx, sy, Color.getColor(Integer.toString(first)));
    }

    private static void erase(int x, int y, int r) {
        for (int i = 0; i < r; i++) {
            for (int j = 0; j < r; j++) {
                imprint(x + i, y + j, Color.BLACK);
            }
        }
    }

    public static void destroy(Particle gr) {
        grains.remove(gr);
    }

    public static void update(int x, int y) {
        int tmp = check(x, y);
    }

    private static void addObject(GraphicComponent comp) {
        objects.add(comp);
    }

    public static void newSand(int x, int y) {
        grains.add(new Sand(
                x + random.nextInt(10) - 5,
                y + random.nextInt(20) - 10,
                (random.nextInt(RNG) - RNG / 2) / 10.,
                -(random.nextInt(RNG) - RNG / 2) / 10.,
                Color.CYAN));
    }
    private Timer tickTimer;
    private Timer graphicTimer;
    private Worm worm;

    public ServerPanel() {
        setFocusable(true);
        setPreferredSize(SIZE);
        //Graphics g = map.getGraphics();
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

    public void init() {
        tickTimer.start();
        graphicTimer.start();
    }

    @Override
    public void paint(Graphics grphcs) {
        super.paint(grphcs);
        Graphics2D g = (Graphics2D) grphcs;
        //g.drawImage(map, null, this);
        g.drawImage(map, 0, 0, getWidth(), getHeight(), null);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        for (Particle particle : grains) {
            particle.tick();
        }
    }
}

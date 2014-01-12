package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import particles.Particle;
import particles.Sand;
import spritesheets.Controls;
import spritesheets.ControlsEnum;

/**
 *
 * @author Skarab
 */
public class MainPanel extends JPanel implements
        ActionListener,
        KeyListener,
        MouseMotionListener,
        MouseListener {

    private static final int RNG = 20;
    public static final Dimension SIZE = new Dimension(800, 600);
    private static Controls controls;
    private static BufferedImage map;
    private static CopyOnWriteArrayList<Particle> grains;
    private static Point mouse;
    private Timer timer;
    private Timer rBrushTimer;
    private Worm worm;
    private Random random;

    public MainPanel() {
        setFocusable(true);
        mouse = new Point();
        random = new Random();
        setPreferredSize(SIZE);
        map = new BufferedImage(SIZE.width, SIZE.height, BufferedImage.TYPE_INT_RGB);
        Graphics g = map.getGraphics();
        grains = new CopyOnWriteArrayList<>();
        timer = new Timer(20, this);
        // when invokeLater is not used, first game is spoiled by a bug
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                init();
            }
        });
        rBrushTimer = new Timer(40, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (int i = 0; i < 10; i++) {
                    newSand(mouse.x, mouse.y);
                }
            }
        });
        controls = new Controls()
                .add(ControlsEnum.Up, 38)
                .add(ControlsEnum.Down, 40)
                .add(ControlsEnum.Right, 39)
                .add(ControlsEnum.Left, 37);
        addMouseListener(this);
        addMouseMotionListener(this);
        addKeyListener(this);
    }

    public void startMoving() {
        timer.start();
    }

    public void init() {
        worm = new Worm(200, 200);
        repaint();
    }

    @Override
    public void paint(Graphics grphcs) {
        super.paint(grphcs);
        Graphics2D g = (Graphics2D) grphcs;
        g.drawImage(map, null, this);
        for (Particle grain : grains) {
            g.setColor(grain.color);
            grain.draw(g);
        }
        if (worm != null) {
            worm.draw(g, this);
        }
        g.scale(4, 4);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        worm.tick();
        for (Particle particle : grains) {
            particle.tick();
        }
        repaint();
    }

    @Override
    public void keyPressed(KeyEvent ke) {
        int i = ke.getKeyCode();
        ControlsEnum en = controls.get(i);
        System.out.println(i + " " + en);
        if (en != null) {
            worm.controlOn(en);
        }
    }

    @Override
    public void keyReleased(KeyEvent ke) {
        int i = ke.getKeyCode();
        ControlsEnum en = controls.get(i);
        System.out.println(i + " " + en);
        if (en != null) {
            worm.controlOff(en);
        }
    }

    public static int check(int x, int y) {
        int rgb;
        try {
            rgb = map.getRGB(x, y);
        } catch (ArrayIndexOutOfBoundsException ex) {
            return 0;
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
        /*if (tmp == Color.CYAN.getRGB()) {
         imprint(new Grain(x, y, 0, 0, Color.BLACK));
         grains.add(new Sand(x, y, 0, 0, Color.CYAN));
         }*/
    }

    public void clear() {
        for (Particle grain : grains) {
            if (grain.clear()) {
                grains.remove(grain);
            }
        }
    }

    public void newSand(int x, int y) {
        grains.add(new Sand(
                x + random.nextInt(10) - 5,
                y + random.nextInt(20) - 10,
                (random.nextInt(RNG) - RNG / 2) / 10.,
                -(random.nextInt(RNG) - RNG / 2) / 10.,
                Color.CYAN));
    }

    @Override
    public void mousePressed(MouseEvent e) {
        mouse.x = e.getX();
        mouse.y = e.getY();
        switch (e.getButton()) {
            case MouseEvent.BUTTON1:
                rBrushTimer.start();
                break;
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        switch (e.getButton()) {
            case MouseEvent.BUTTON1:
                rBrushTimer.stop();
                break;
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        mouse.x = e.getX();
        mouse.y = e.getY();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }
}

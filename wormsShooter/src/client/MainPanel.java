package client;

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
import java.rmi.RemoteException;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import objects.Controls;
import objects.ControlsEnum;
import objects.GraphicComponent;
import objects.TestBody;
import particles.Particle;
import particles.Sand;

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
    public static final Dimension VIEW_SIZE = new Dimension(64, 48);
    public static final Color BACKGROUND = Color.BLUE;
    private static Controls controls;
    private static BufferedImage map;
    private static BufferedImage curentView;
    private static Point currentPosition;
    private static CopyOnWriteArrayList<Particle> grains;
    private static CopyOnWriteArrayList<GraphicComponent> objects;
    private static Point mouse;
    private static Random random;

    static {
        Dimension mapSize = SIZE;
        try {
            mapSize = ClientCommunication.getInstance().getSize();
        } catch (RemoteException ex) {
            Logger.getLogger(MainPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
        map = new BufferedImage(mapSize.width, mapSize.height, BufferedImage.TYPE_INT_RGB);
        curentView = new BufferedImage(VIEW_SIZE.width, VIEW_SIZE.height, BufferedImage.TYPE_INT_RGB);
        currentPosition = new Point(30, 20);
        random = new Random();
    }

    public static void loadAllChunks() {
        try {
            map = ClientCommunication.getInstance().getMap();
        } catch (RemoteException ex) {
            Logger.getLogger(MainPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void loadChunk(int x, int y) {
        Color ret = Color.BLACK;
        try {
            ret = ClientCommunication.getInstance().getPixel(x, y);
        } catch (RemoteException ex) {
            Logger.getLogger(MainPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println(ret);
        imprint(x, y, ret);
    }

    public static Color check(int x, int y) {
        int rgb;
        try {
            rgb = map.getRGB(x, y);
        } catch (ArrayIndexOutOfBoundsException ex) {
            return Color.BLACK;
        }
        return new Color(rgb);
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
        Color first = check(x, y);
        Color second = check(sx, sy);
        imprint(x, y, second);
        imprint(sx, sy, first);
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
    private Timer timer;
    private Timer rBrushTimer;
    private TestBody body;

    public MainPanel() {
        body = new TestBody(30, 20);
        mouse = new Point();
        grains = new CopyOnWriteArrayList<>();
        objects = new CopyOnWriteArrayList<>();
        setFocusable(true);
        setPreferredSize(SIZE);
        // when invokeLater is not used, first game is spoiled by a bug
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                init();
            }
        });
    }

    public void init() {
        timer = new Timer(20, this);
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
                .add(ControlsEnum.Left, 37)
                .add(ControlsEnum.Fire, 32);
        repaint();
        addMouseListener(this);
        addMouseMotionListener(this);
        addKeyListener(this);
        timer.start();
        loadAllChunks();
    }

    @Override
    public void paint(Graphics grphcs) {
        super.paint(grphcs);
        currentPosition.x = body.getPosition().x;
        currentPosition.y = body.getPosition().y;
        curentView = map.getSubimage(currentPosition.x, currentPosition.y, VIEW_SIZE.width, VIEW_SIZE.height);
        Graphics2D g = (Graphics2D) grphcs;
        //g.drawImage(map, null, this);
        g.drawImage(curentView, 0, 0, getWidth(), getHeight(), null);
        body.draw(g);
        /*for (Particle grain : grains) {
         g.setColor(grain.color);
         grain.draw(g);
         }
         for (GraphicComponent obj : objects) {
         obj.draw(g);
         }
         if (worm != null) {
         worm.draw(g);
         }
         g.scale(4, 4);*/
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        body.tick();
        for (Particle particle : grains) {
            particle.tick();
        }
        repaint();
    }

    @Override
    public void keyPressed(KeyEvent ke) {
        int i = ke.getKeyCode();
        ControlsEnum en = controls.get(i);
        body.controlOn(en);
        /*switch (en) {
         case Up:
         currentPosition.y--;
         break;
         case Down:
         currentPosition.y++;
         break;
         case Left:
         currentPosition.x--;
         break;
         case Right:
         currentPosition.x++;
         break;
         }*/
        /*if (en != null) {
         worm.controlOn(en);
         }*/
    }

    @Override
    public void keyReleased(KeyEvent ke) {
        int i = ke.getKeyCode();
        ControlsEnum en = controls.get(i);
        body.controlOff(en);
        /*if (en != null) {
         worm.controlOff(en);
         }*/
    }

    public void clear() {
        for (Particle grain : grains) {
            if (grain.clear()) {
                grains.remove(grain);
            }
        }
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

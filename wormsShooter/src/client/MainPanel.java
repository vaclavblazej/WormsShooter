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
import java.awt.image.RasterFormatException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;
import javax.swing.Timer;
import objects.Body;
import objects.CollisionState;
import objects.Controls;
import objects.ControlsEnum;
import objects.GraphicComponent;
import objects.Part;
import objects.TestBody;
import particles.Particle;
import particles.Sand;
import spritesheets.SpriteLoader;
import utilities.MapInterface;
import utilities.Materials;

/**
 *
 * @author Skarab
 */
public class MainPanel extends JPanel implements
        MapInterface,
        ActionListener,
        KeyListener,
        MouseMotionListener,
        MouseListener {

    private static final int RNG = 20;
    public static final int RATIO = 20;
    public static final Dimension SIZE = new Dimension(800, 600);
    public static final Dimension VIEW_SIZE = new Dimension(SIZE.width / RATIO, SIZE.height / RATIO);
    private static Controls controls;
    private static BufferedImage map;
    private static BufferedImage curentView;
    private static Point currentPosition;
    private static CopyOnWriteArrayList<Particle> grains;
    private static CopyOnWriteArrayList<GraphicComponent> objects;
    private static Point mouse;
    private static Random random;
    private static MainPanel instance;

    static {
        map = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);
        curentView = new BufferedImage(VIEW_SIZE.width, VIEW_SIZE.height, BufferedImage.TYPE_INT_RGB);
        currentPosition = new Point(30, 20);
        random = new Random();
    }

    public static MainPanel getInstance() {
        if (instance == null) {
            instance = new MainPanel();
        }
        return instance;
    }
    private Timer timer;
    private Timer rBrushTimer;
    private TestBody body;
    private EnumSet<ControlsEnum> controlSet;
    private List<Body> bodies;

    private MainPanel() {
        controlSet = EnumSet.noneOf(ControlsEnum.class);
        body = new TestBody(100, 100, RATIO, this);
        mouse = new Point();
        grains = new CopyOnWriteArrayList<>();
        objects = new CopyOnWriteArrayList<>();
        bodies = new ArrayList<>(10);
        setFocusable(true);
        setPreferredSize(SIZE);
        /*SwingUtilities.invokeLater(new Runnable() {
         @Override
         public void run() {
         init();
         }
         });*/
        test();
    }
    
    public void addBody(Body body){
        bodies.add(body);
    }
    
    private void test(){
        Body t = new Body(20, 20);
        SpriteLoader.loadSprite("WormHead");
        SpriteLoader.set(15, 15);
        t.addPart(new Part(SpriteLoader.getRawSprite()));
        addBody(t);
    }

    public void loadAllChunks() {
        try {
            map = ClientCommunication.getInstance().getMap();
        } catch (RemoteException ex) {
            Logger.getLogger(MainPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void loadChunk(int x, int y) {
        Color ret = Color.BLACK;
        try {
            ret = ClientCommunication.getInstance().getPixel(x, y);
        } catch (RemoteException ex) {
            Logger.getLogger(MainPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println(ret);
        imprint(x, y, ret);
    }

    @Override
    public CollisionState check(int x, int y) {
        return Materials.check(getPixel(x, y));
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

    private void addObject(GraphicComponent comp) {
        objects.add(comp);
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
        Dimension mapSize = SIZE;
        try {
            mapSize = ClientCommunication.getInstance().getSize();
        } catch (RemoteException ex) {
            Logger.getLogger(MainPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
        map = new BufferedImage(mapSize.width, mapSize.height, BufferedImage.TYPE_INT_RGB);
        timer = new Timer(20, this);
        rBrushTimer = new Timer(40, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (int i = 0; i < 10; i++) {
                    newSand(mouse.x, mouse.y);
                }
            }
        });
        controls = Settings.getInstance().getControls();
        Settings.getInstance().setControls(controls);
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
        currentPosition.x = body.getPosition().x - VIEW_SIZE.width / 2;
        currentPosition.y = body.getPosition().y - VIEW_SIZE.height / 2;
        try {
            curentView = map.getSubimage(currentPosition.x, currentPosition.y, VIEW_SIZE.width, VIEW_SIZE.height);
        } catch (RasterFormatException ex) {
        }
        Graphics2D g = (Graphics2D) grphcs;
        g.drawImage(curentView, 0, 0, getWidth(), getHeight(), null);
        g.translate(RATIO * VIEW_SIZE.width / 2, RATIO * VIEW_SIZE.height / 2);
        body.drawRelative(g);
        for (Body b : bodies) {
            b.draw(g);
        }
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
        if (en != null && !controlSet.contains(en)) {
            try {
                ClientCommunication.getInstance().sendAction(en, true);
            } catch (RemoteException ex) {
                Logger.getLogger(MainPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
            controlSet.add(en);
        }
    }

    @Override
    public void keyReleased(KeyEvent ke) {
        int i = ke.getKeyCode();
        ControlsEnum en = controls.get(i);
        if (en != null && controlSet.contains(en)) {
            try {
                ClientCommunication.getInstance().sendAction(en, false);
            } catch (RemoteException ex) {
                Logger.getLogger(MainPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
            controlSet.remove(en);
        }
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

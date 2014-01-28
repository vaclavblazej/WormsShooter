package client;

import client.menu.Settings;
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
import java.util.Collection;
import java.util.EnumSet;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;
import javax.swing.Timer;
import objects.GraphicComponent;
import objects.Miscellaneous;
import objects.TestBody;
import particles.Particle;
import particles.Sand;
import spritesheets.SpriteLoader;
import utilities.CollisionState;
import utilities.Controls;
import utilities.ControlsEnum;
import utilities.MapInterface;
import utilities.Materials;
import utilities.communication.Action;
import utilities.communication.Model;
import utilities.communication.PacketBuilder;

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

    public static final int RATIO = 20;
    public static final Dimension SIZE = new Dimension(800, 600);
    public static final Dimension VIEW_SIZE = new Dimension(SIZE.width / RATIO, SIZE.height / RATIO);
    private static final int RNG = 20;
    private static Controls controls;
    private static CopyOnWriteArrayList<Particle> grains;
    private static Point mouse;
    private static MainPanel instance;

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
    private Collection<TestBody> bodies;
    private CopyOnWriteArrayList<GraphicComponent> objects;
    private BufferedImage map;
    private BufferedImage curentView;
    private BufferedImage realView;
    private Point currentPosition;
    private Random random;
    private Model model;

    private MainPanel() {
        random = new Random();
        map = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);
        realView = new BufferedImage(SIZE.width, SIZE.height, BufferedImage.TYPE_INT_RGB);
        curentView = new BufferedImage(VIEW_SIZE.width, VIEW_SIZE.height, BufferedImage.TYPE_INT_RGB);
        currentPosition = new Point(30, 20);
        controlSet = EnumSet.noneOf(ControlsEnum.class);
        body = new TestBody(100, 100, this);
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
        //test();
    }

    private void test() {
        SpriteLoader.loadSprite("Campfire");
        SpriteLoader.set(20, 20);
        Miscellaneous m = new Miscellaneous(SpriteLoader.getSprite(), 100, 100);
        addObject(m);
    }

    public Model getModel() {
        return model;
    }

    public void setModel(Model model) {
        map = model.getMap();
        bodies = model.getControls().values();
        this.model = model;
    }

    public void change(Point point, Materials mat) {
        Graphics g = map.getGraphics();
        g.setColor(mat.getColor());
        g.drawLine(point.x, point.y, point.x, point.y);
    }

    public void addBody(TestBody body) {
        bodies.add(body);
    }

    public TestBody newBody() {
        TestBody body = new TestBody(100, 100, this);
        bodies.add(body);
        return body;
    }

    public void setMyBody(TestBody body) {
        this.body = body;
    }

    public TestBody getMyBody() {
        return body;
    }

    @Override
    public int getRatio() {
        return RATIO;
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
        timer = new Timer(40, this);
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
    }

    @Override
    public void paint(Graphics grphcs) {
        super.paint(grphcs);
        if (body != null) {
            currentPosition.x = body.getPosition().x - VIEW_SIZE.width / 2;
            currentPosition.y = body.getPosition().y - VIEW_SIZE.height / 2;
        }
        try {
            curentView = map.getSubimage(currentPosition.x, currentPosition.y, VIEW_SIZE.width, VIEW_SIZE.height);
            Materials.redraw(curentView, realView);
        } catch (RasterFormatException ex) {
        }
        // todo - fix exception when you are near end of the map
        Graphics2D g = (Graphics2D) grphcs;
        g.drawImage(realView, 0, 0, getWidth(), getHeight(), null);
        g.translate(RATIO * VIEW_SIZE.width / 2, RATIO * VIEW_SIZE.height / 2);
        for (TestBody b : bodies) {
            b.drawRelative(g);
        }
        for (GraphicComponent o : objects) {
            o.draw(g);
        }
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        for (TestBody b : bodies) {
            b.tick();
        }
        repaint();
    }

    @Override
    public void keyPressed(KeyEvent ke) {
        int i = ke.getKeyCode();
        ControlsEnum en = controls.get(i);
        if (en != null && controlSet.add(en)) {
            try {
                switch (en) {
                    case MINE:
                        ClientCommunication.getInstance().sendAction(new PacketBuilder(Action.MINE));
                        break;
                    case UP:
                        ClientCommunication.getInstance().sendAction(new PacketBuilder(Action.MOVE_JUMP));
                        break;
                    case LEFT:
                    case RIGHT:
                        changeMovement();
                        break;
                }

            } catch (RemoteException ex) {
                Logger.getLogger(MainPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent ke) {
        int i = ke.getKeyCode();
        ControlsEnum en = controls.get(i);
        if (en != null && controlSet.remove(en)) {
            switch (en) {
                case LEFT:
                case RIGHT:
                    changeMovement();
                    break;
            }
        }
    }

    public void changeMovement() {
        try {
            int d = 0;
            if (controlSet.contains(ControlsEnum.LEFT)) {
                d--;
            }
            if (controlSet.contains(ControlsEnum.RIGHT)) {
                d++;
            }
            switch (d) {
                case 1:
                    ClientCommunication.getInstance().sendAction(new PacketBuilder(Action.MOVE_RIGHT));
                    break;
                case 0:
                    ClientCommunication.getInstance().sendAction(new PacketBuilder(Action.MOVE_STOP));
                    break;
                case -1:
                    ClientCommunication.getInstance().sendAction(new PacketBuilder(Action.MOVE_LEFT));
                    break;
            }
        } catch (RemoteException ex) {
            Logger.getLogger(MainPanel.class.getName()).log(Level.SEVERE, null, ex);
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

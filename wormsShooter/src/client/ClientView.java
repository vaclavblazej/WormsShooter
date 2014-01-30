package client;

import client.menu.Settings;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.awt.image.RasterFormatException;
import java.rmi.RemoteException;
import java.util.EnumSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import objects.GraphicComponent;
import objects.Miscellaneous;
import objects.TestBody;
import particles.Particle;
import spritesheets.SpriteLoader;
import utilities.Controls;
import utilities.ControlsEnum;
import utilities.ViewInterface;
import utilities.communication.Action;
import utilities.communication.Model;
import utilities.communication.PacketBuilder;
import utilities.materials.MaterialVisuals;

/**
 *
 * @author Skarab
 */
public class ClientView extends ViewInterface implements
        ActionListener,
        KeyListener,
        MouseMotionListener,
        MouseListener {

    private final Dimension VIEW_SIZE;
    private static Controls controls;
    private static Point mouse;
    private static ClientView instance;

    public static ClientView getInstance() {
        if (instance == null) {
            instance = new ClientView();
        }
        return instance;
    }
    private TestBody body;
    private EnumSet<ControlsEnum> controlSet;
    private BufferedImage curentView;
    private BufferedImage realView;
    private Point currentPosition;

    private ClientView() {
        super(800, 600, 20);
        map = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);
        realView = new BufferedImage(800, 600, BufferedImage.TYPE_INT_RGB);
        VIEW_SIZE = new Dimension(800 / getRatio(), 600 / getRatio());
        curentView = new BufferedImage(VIEW_SIZE.width, VIEW_SIZE.height, BufferedImage.TYPE_INT_RGB);
        currentPosition = new Point(30, 20);
        controlSet = EnumSet.noneOf(ControlsEnum.class);
        body = new TestBody(100, 100, this);
        mouse = new Point();
    }

    private void test() {
        SpriteLoader.loadSprite("Campfire");
        SpriteLoader.set(20, 20);
        Miscellaneous m = new Miscellaneous(SpriteLoader.getSprite(), 100, 100);
        addObject(m);
    }

    @Override
    public Model getModel() {
        return model;
    }

    @Override
    public void setModel(Model model) {
        super.setModel(model);
        map = model.getMap();
        bodies = model.getControls().values();
    }

    public void addBody(TestBody body) {
        bodies.add(body);
    }

    public void setMyBody(TestBody body) {
        this.body = body;
    }

    public TestBody getMyBody() {
        return body;
    }

    private void addObject(GraphicComponent comp) {
        objects.add(comp);
    }

    public void init() {
        super.init();
        controls = Settings.getInstance().getControls();
        Settings.getInstance().setControls(controls);
        repaint();
        addMouseListener(this);
        addMouseMotionListener(this);
        addKeyListener(this);
    }

    @Override
    public void paint(Graphics grphcs) {
        super.paint(grphcs);
        if (body != null) {
            currentPosition.x = body.getPosition().x - VIEW_SIZE.width / 2;
            currentPosition.y = body.getPosition().y - VIEW_SIZE.height / 2;
        }
        try {
            curentView = map.getSubimage(currentPosition.x, currentPosition.y,
                    VIEW_SIZE.width, VIEW_SIZE.height);
            MaterialVisuals.redraw(curentView, realView);
        } catch (RasterFormatException ex) {
            // todo - fix exception when you are near end of the map
        }
        Graphics2D g = (Graphics2D) grphcs;
        g.drawImage(realView, 0, 0, getWidth(), getHeight(), null);
        g.translate(getRatio() * VIEW_SIZE.width / 2, getRatio() * VIEW_SIZE.height / 2);
        if (body != null) {
            body.drawRelative(g);
        }
        for (GraphicComponent o : objects) {
            o.draw(g);
        }
    }

    @Override
    public void keyPressed(KeyEvent ke) {
        int i = ke.getKeyCode();
        ControlsEnum en = controls.get(i);
        if (en != null && controlSet.add(en)) {
            try {
                switch (en) {
                    case UP:
                        ClientCommunication.getInstance().sendAction(new PacketBuilder(Action.MOVE_JUMP));
                        break;
                    case LEFT:
                    case RIGHT:
                        changeMovement();
                        break;
                }

            } catch (RemoteException ex) {
                Logger.getLogger(ClientView.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(ClientView.class.getName()).log(Level.SEVERE, null, ex);
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
                int x = currentPosition.x + e.getX() / getRatio();
                int y = currentPosition.y + e.getY() / getRatio();
                try {
                    ClientCommunication.getInstance().sendAction(
                            new PacketBuilder(Action.MINE).addInfo(new Point(x, y)));
                } catch (RemoteException ex) {
                    Logger.getLogger(ClientView.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        switch (e.getButton()) {
            case MouseEvent.BUTTON1:
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

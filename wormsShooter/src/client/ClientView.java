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
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.RasterFormatException;
import java.util.ArrayList;
import java.util.EnumSet;
import main.Main;
import objects.Body;
import objects.GraphicComponent;
import objects.MoveAction;
import objects.items.ItemBlueprint;
import objects.items.itemActions.ItemAction;
import utilities.AbstractView;
import utilities.Controls;
import utilities.ControlsEnum;
import utilities.MapClass;
import utilities.communication.Action;
import utilities.communication.Model;
import utilities.communication.PacketBuilder;
import utilities.materials.MaterialVisuals;

/**
 *
 * @author Skarab
 */
public class ClientView extends AbstractView implements
        ActionListener,
        KeyListener,
        MouseMotionListener,
        MouseListener {

    private static ClientView instance;

    public static ClientView getInstance() {
        if (instance == null) {
            instance = new ClientView();
        }
        return instance;
    }
    private static final int SCALE = 20;
    private final Dimension VIEW_SIZE;
    private Body body;
    private EnumSet<ControlsEnum> controlSet;
    private MapClass curentView;
    private BufferedImage realView;
    private Point currentPosition;
    private AffineTransform tr;
    private Controls controls;
    private Point mouse;

    private ClientView() {
        super(800, 600, SCALE);
        map = new MapClass(new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB), this);
        realView = new BufferedImage(800, 600, BufferedImage.TYPE_INT_RGB);
        VIEW_SIZE = new Dimension(800 / getRatio(), 600 / getRatio());
        curentView = null;
        currentPosition = new Point();
        controlSet = EnumSet.noneOf(ControlsEnum.class);
        mouse = new Point();
        tr = new AffineTransform();
        tr.setToScale(SCALE, SCALE);
    }

    @Override
    public void setModel(Model model) {
        super.setModel(model);
        map = model.getMap();
        map.calculateShadows();
        bodies = new ArrayList<>(model.getControls().values());
    }

    public void setMyBody(Body body) {
        this.body = body;
    }

    public Body getMyBody() {
        return body;
    }

    @Override
    public void init() {
        super.init();
        controls = Settings.getInstance().getControls();
        repaint();
        addMouseListener(this);
        addMouseMotionListener(this);
        addKeyListener(this);
    }

    @Override
    public void reset() {
        super.reset();
        removeMouseListener(this);
        removeMouseMotionListener(this);
        removeKeyListener(this);
        body = null;
        controls = null;
        repaint();
    }

    @Override
    public void paint(Graphics grphcs) {
        super.paint(grphcs);
        if (body != null) {
            Point bodyPosition = body.getPosition();
            currentPosition.x = (int) ((bodyPosition.x / Main.RATIO - VIEW_SIZE.width / 2));
            currentPosition.y = (int) ((bodyPosition.y / Main.RATIO - VIEW_SIZE.height / 2));
            if (currentPosition.x < 0) {
                currentPosition.x = 0;
            } else if (currentPosition.x > map.getWidth() - VIEW_SIZE.width) {
                currentPosition.x = map.getWidth() - VIEW_SIZE.width;
            }
            if (currentPosition.y < 0) {
                currentPosition.y = 0;
            } else if (currentPosition.y > map.getHeight() - VIEW_SIZE.height) {
                currentPosition.y = map.getHeight() - VIEW_SIZE.height;
            }
        }
        tr.setToTranslation(currentPosition.x * Main.RATIO, currentPosition.y * Main.RATIO);
        try {
            curentView = map.getSubmap(currentPosition.x, currentPosition.y,
                    VIEW_SIZE.width, VIEW_SIZE.height);
            MaterialVisuals.redraw(curentView, realView);
        } catch (RasterFormatException | ArrayIndexOutOfBoundsException ex) {
        }
        Graphics2D g = (Graphics2D) grphcs;
        g.drawImage(realView, 0, 0, getWidth(), getHeight(), null);
        g.translate(getRatio() * VIEW_SIZE.width / 2, getRatio() * VIEW_SIZE.height / 2);
        for (Body b : bodies) {
            b.drawRelative(g, tr);
        }
        for (GraphicComponent o : objects) {
            o.drawRelative(g, tr);
        }
    }

    @Override
    public void keyPressed(KeyEvent ke) {
        int i = ke.getKeyCode();
        ControlsEnum en = controls.get(i);
        if (en != null && controlSet.add(en)) {
            switch (en) {
                case UP:
                    ClientCommunication.getInstance().send(
                            new PacketBuilder(Action.MOVE).addInfo(MoveAction.JUMP));
                    break;
                case LEFT:
                case RIGHT:
                    changeMovement();
                    break;
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
        int d = 0;
        if (controlSet.contains(ControlsEnum.LEFT)) {
            d--;
        }
        if (controlSet.contains(ControlsEnum.RIGHT)) {
            d++;
        }
        switch (d) {
            case 1:
                ClientCommunication.getInstance().send(new PacketBuilder(Action.MOVE).addInfo(MoveAction.RIGHT));
                break;
            case 0:
                ClientCommunication.getInstance().send(new PacketBuilder(Action.MOVE).addInfo(MoveAction.STOP));
                break;
            case -1:
                ClientCommunication.getInstance().send(new PacketBuilder(Action.MOVE).addInfo(MoveAction.LEFT));
                break;
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        mouse.x = e.getX();
        mouse.y = e.getY();
        switch (e.getButton()) {
            case MouseEvent.BUTTON1:
                Point p = new Point((int) (currentPosition.x * Main.RATIO) + e.getX(),
                        (int) (currentPosition.y * Main.RATIO) + e.getY());
                ItemBlueprint heldItem = getMyBody().getInventory().getHeldItem();
                if (heldItem != null) {
                    ItemAction action = heldItem.getAction();
                    if (action != null) {
                        action.action(p);
                    }
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

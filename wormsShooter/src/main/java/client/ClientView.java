package client;

import client.actions.impl.MoveAction;
import client.menu.Settings;
import main.Application;
import objects.Body;
import objects.GraphicComponent;
import objects.MoveEnum;
import objects.items.ItemBlueprint;
import objects.items.itemActions.ItemAction;
import utilities.AbstractView;
import utilities.Controls;
import utilities.ControlsEnum;
import utilities.MapClass;
import utilities.communication.Model;
import utilities.materials.MaterialVisuals;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.RasterFormatException;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Václav Blažej
 */
public class ClientView extends AbstractView implements
        ActionListener,
        KeyListener,
        MouseMotionListener,
        MouseListener,
        ComponentListener {

    private static final Logger logger = Logger.getLogger(ClientView.class.getName());

    private static ClientView instance;
    private static final int SCALE = 20;

    public static ClientView getInstance() {
        if (instance == null) instance = new ClientView();
        return instance;
    }

    private Dimension tileViewDimensions;
    private Dimension finalViewDimensions;
    private Body body;
    private EnumSet<ControlsEnum> controlSet;
    private MapClass currentView;
    private BufferedImage finalView;
    private Point viewTilePos;
    private AffineTransform tr;         // Defines view position and size. Is inverted already.
    private Controls controls;
    private Point mouse;

    private ClientView() {
        super(SCALE);
        map = new MapClass(new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB), this);
        finalViewDimensions = new Dimension(800, 600);
        finalView = new BufferedImage(
                finalViewDimensions.width + (int) Application.RATIO,
                finalViewDimensions.height + (int) Application.RATIO,
                BufferedImage.TYPE_INT_RGB);
        tileViewDimensions = new Dimension(
                finalViewDimensions.width / getRatio(),
                finalViewDimensions.height / getRatio());
        currentView = null;
        viewTilePos = new Point();
        controlSet = EnumSet.noneOf(ControlsEnum.class);
        mouse = new Point();
        tr = new AffineTransform();
        final double scaleTmp = 1.0 / SCALE;
        tr.setToScale(scaleTmp, scaleTmp);
        setFocusable(true);
    }

    @Override
    public void setModel(Model model) {
        super.setModel(model);
        map = model.getMap();
//        map.calculateShadows(material);
        bodies = new ArrayList<>(model.getControls().values());
    }

    public void setMyView(Body body) {
        this.body = body;
    }

    public Body getMyView() {
        return body;
    }

    @Override
    public void init() {
        super.init();
        controls = Settings.getInstance().getControls();
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
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        Point smoothOffset = new Point(0, 0);
        Point viewRealPos = new Point(0, 0);
        if (body != null) {
            Point bodyPosition = body.getPosition();
            smoothOffset.x = (int) (bodyPosition.x % Application.RATIO);
            smoothOffset.y = (int) (bodyPosition.y % Application.RATIO);
            viewTilePos.x = (int) ((bodyPosition.x / Application.RATIO - tileViewDimensions.width / 2));
            viewTilePos.y = (int) ((bodyPosition.y / Application.RATIO - tileViewDimensions.height / 2));
            viewRealPos.x = (int) (bodyPosition.x - Application.RATIO * tileViewDimensions.width / 2);
            viewRealPos.y = (int) (bodyPosition.y - Application.RATIO * tileViewDimensions.height / 2);
            int ubX = (int) (map.getWidth() * Application.RATIO) - finalViewDimensions.width - 1;
            int tubX = map.getWidth() - tileViewDimensions.width - 1;
            int ubY = (int) (map.getHeight() * Application.RATIO) - finalViewDimensions.height - 1;
            int tubY = map.getHeight() - tileViewDimensions.height - 1;
            if (viewRealPos.x < 0) {
                viewRealPos.x = 0;
                viewTilePos.x = 0;
                smoothOffset.x = 0;
            } else if (viewRealPos.x > ubX) {
                viewRealPos.x = ubX;
                smoothOffset.x = (int) Application.RATIO;
                viewTilePos.x = tubX;
            }
            if (viewRealPos.y < 0) {
                viewRealPos.y = 0;
                viewTilePos.y = 0;
                smoothOffset.y = 0;
            } else if (viewRealPos.y > ubY) {
                viewRealPos.y = ubY;
                smoothOffset.y = (int) Application.RATIO;
                viewTilePos.y = tubY;
            }
        }
        tr.setToTranslation(-viewRealPos.x, -viewRealPos.y);
        try {
            currentView = map.getSubmap(viewTilePos.x, viewTilePos.y,
                    tileViewDimensions.width + 1, tileViewDimensions.height + 1);
            MaterialVisuals.redraw(currentView, finalView);
        } catch (RasterFormatException | ArrayIndexOutOfBoundsException ex) {
            logger.log(Level.SEVERE, null, ex);
        }
        Graphics2D g = (Graphics2D) graphics;
        g.drawImage(finalView, -smoothOffset.x, -smoothOffset.y, finalView.getWidth(), finalView.getHeight(), null);
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
                    ClientCommunication.getInstance().send(new MoveAction(MoveEnum.JUMP));
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
        if (controlSet.contains(ControlsEnum.LEFT)) d--;
        if (controlSet.contains(ControlsEnum.RIGHT)) d++;
        switch (d) {
            case 1:
                ClientCommunication.getInstance().send(new MoveAction(MoveEnum.RIGHT));
                break;
            case 0:
                ClientCommunication.getInstance().send(new MoveAction(MoveEnum.STOP));
                break;
            case -1:
                ClientCommunication.getInstance().send(new MoveAction(MoveEnum.LEFT));
                break;
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        mouse.x = e.getX();
        mouse.y = e.getY();
        switch (e.getButton()) {
            case MouseEvent.BUTTON1:
                Point p = new Point((int) (viewTilePos.x * Application.RATIO) + e.getX(),
                        (int) (viewTilePos.y * Application.RATIO) + e.getY());
                ItemBlueprint heldItem = getMyView().getInventory().getHeldItem();
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

    @Override
    public void componentResized(ComponentEvent e) {
        finalViewDimensions = getSize();
        finalView = new BufferedImage(
                finalViewDimensions.width + (int) Application.RATIO,
                finalViewDimensions.height + (int) Application.RATIO,
                BufferedImage.TYPE_INT_RGB);
        tileViewDimensions = new Dimension(
                finalViewDimensions.width / getRatio(),
                finalViewDimensions.height / getRatio());
    }

    @Override
    public void componentMoved(ComponentEvent e) {
    }

    @Override
    public void componentShown(ComponentEvent e) {
    }

    @Override
    public void componentHidden(ComponentEvent e) {
    }
}

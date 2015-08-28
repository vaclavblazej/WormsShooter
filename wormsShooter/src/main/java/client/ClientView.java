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

/**
 * @author Václav Blažej
 */
public class ClientView extends AbstractView implements
        ActionListener,
        KeyListener,
        MouseMotionListener,
        MouseListener {

    private static ClientView instance;
    private static final int SCALE = 20;

    public static ClientView getInstance() {
        if (instance == null) instance = new ClientView();
        return instance;
    }

    private final Dimension TILE_VIEW_SIZE;
    private final Dimension REAL_VIEW_SIZE;
    private Body body;
    private EnumSet<ControlsEnum> controlSet;
    private MapClass curentView;
    private BufferedImage realView;
    private Point viewTilePos;
    private AffineTransform tr;         // Defines view position and size. Is inversed already.
    private Controls controls;
    private Point mouse;

    private ClientView() {
        super(800, 600, SCALE);
        map = new MapClass(new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB), this);
        REAL_VIEW_SIZE = new Dimension(800, 600);
        realView = new BufferedImage(
                REAL_VIEW_SIZE.width + (int) Application.RATIO,
                REAL_VIEW_SIZE.height + (int) Application.RATIO,
                BufferedImage.TYPE_INT_RGB);
        TILE_VIEW_SIZE = new Dimension(
                REAL_VIEW_SIZE.width / getRatio(),
                REAL_VIEW_SIZE.height / getRatio());
        curentView = null;
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
        //repaint();
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
        Point harmony = new Point(0, 0);
        Point viewRealPos = new Point(0, 0);
        if (body != null) {
            Point bodyPosition = body.getPosition();
            harmony.x = (int) (bodyPosition.x % Application.RATIO);
            harmony.y = (int) (bodyPosition.y % Application.RATIO);
            viewTilePos.x = (int) ((bodyPosition.x / Application.RATIO - TILE_VIEW_SIZE.width / 2));
            viewTilePos.y = (int) ((bodyPosition.y / Application.RATIO - TILE_VIEW_SIZE.height / 2));
            viewRealPos.x = (int) (bodyPosition.x - Application.RATIO * TILE_VIEW_SIZE.width / 2);
            viewRealPos.y = (int) (bodyPosition.y - Application.RATIO * TILE_VIEW_SIZE.height / 2);
            int ubX = (int) (map.getWidth() * Application.RATIO) - REAL_VIEW_SIZE.width - 1;
            int tubX = map.getWidth() - TILE_VIEW_SIZE.width - 1;
            int ubY = (int) (map.getHeight() * Application.RATIO) - REAL_VIEW_SIZE.height - 1;
            int tubY = map.getHeight() - TILE_VIEW_SIZE.height - 1;
            if (viewRealPos.x < 0) {
                viewRealPos.x = 0;
                viewTilePos.x = 0;
                harmony.x = 0;
            } else if (viewRealPos.x > ubX) {
                viewRealPos.x = ubX;
                harmony.x = (int) Application.RATIO;
                viewTilePos.x = tubX;
            }
            if (viewRealPos.y < 0) {
                viewRealPos.y = 0;
                viewTilePos.y = 0;
                harmony.y = 0;
            } else if (viewRealPos.y > ubY) {
                viewRealPos.y = ubY;
                harmony.y = (int) Application.RATIO;
                viewTilePos.y = tubY;
            }
        }
        tr.setToTranslation(-viewRealPos.x, -viewRealPos.y);
        try {
            curentView = map.getSubmap(viewTilePos.x, viewTilePos.y,
                    TILE_VIEW_SIZE.width + 1, TILE_VIEW_SIZE.height + 1);
            MaterialVisuals.redraw(curentView, realView);
        } catch (RasterFormatException | ArrayIndexOutOfBoundsException ex) {
            //Logger.getLogger(ClientView.class.getName()).log(Level.SEVERE, null, ex);
        }
        Graphics2D g = (Graphics2D) grphcs;
        g.drawImage(realView, -harmony.x, -harmony.y, realView.getWidth(), realView.getHeight(), null);
        for (Body b : bodies) {
            b.drawRelative(g, tr);
        }
        for (GraphicComponent o : objects) {
            o.drawRelative(g, tr);
        }
        chatLog.draw(g);
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
        if (controlSet.contains(ControlsEnum.LEFT)) {
            d--;
        }
        if (controlSet.contains(ControlsEnum.RIGHT)) {
            d++;
        }
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

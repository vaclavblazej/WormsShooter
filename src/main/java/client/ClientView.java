package client;

import client.actions.impl.MoveAction;
import client.menu.Settings;
import main.Application;
import objects.Body;
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

    public static ClientView getInstance() {
        if (instance == null) instance = new ClientView();
        return instance;
    }

    private Dimension tileViewDimensions;
    private Dimension panelViewDimensions;
    private Body body;
    private EnumSet<ControlsEnum> controlSet;
    private MapClass currentView;
    private BufferedImage rasteredView;
    private Point viewTilePos;
    private AffineTransform transformation;         // Defines view position and size. Is inverted already.
    private Controls controls;
    private Point mouse;

    private ClientView() {
        super(Application.BLOCK_SIZE);
        map = new MapClass(new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB), this);
        recalculateGraphicWindowLayout();
        currentView = null;
        viewTilePos = new Point();
        controlSet = EnumSet.noneOf(ControlsEnum.class);
        mouse = new Point();
        transformation = new AffineTransform();
        // todo ratio with scale
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
            // view point
            Point bodyPosition = body.getPosition();
            smoothOffset.x = bodyPosition.x % Application.BLOCK_SIZE;
            smoothOffset.y = bodyPosition.y % Application.BLOCK_SIZE;
            viewRealPos.x = bodyPosition.x - Application.BLOCK_SIZE * tileViewDimensions.width / 2;
            viewRealPos.y = bodyPosition.y - Application.BLOCK_SIZE * tileViewDimensions.height / 2;

            // map edges
            int ubX = map.getWidth() * Application.BLOCK_SIZE - panelViewDimensions.width - 1;
            if (viewRealPos.x < 0) {
                viewRealPos.x = 0;
                smoothOffset.x = 0;
            } else if (viewRealPos.x > ubX) {
                viewRealPos.x = ubX;
                smoothOffset.x = Application.BLOCK_SIZE;
            }
            int ubY = map.getHeight() * Application.BLOCK_SIZE - panelViewDimensions.height - 1;
            if (viewRealPos.y < 0) {
                viewRealPos.y = 0;
                smoothOffset.y = 0;
            } else if (viewRealPos.y > ubY) {
                viewRealPos.y = ubY;
                smoothOffset.y = Application.BLOCK_SIZE;
            }
            viewTilePos.x = viewRealPos.x / Application.BLOCK_SIZE;
            viewTilePos.y = viewRealPos.y / Application.BLOCK_SIZE;

            // print to screen
            try {
                currentView = map.getSubmap(viewTilePos, tileViewDimensions);
            } catch (RasterFormatException | ArrayIndexOutOfBoundsException ex) {
                logger.log(Level.SEVERE, null, ex);
            }
            MaterialVisuals.redraw(currentView, rasteredView);
            Graphics2D g = (Graphics2D) graphics;
            transformation.setToTranslation(-viewRealPos.x + smoothOffset.x, -viewRealPos.y + smoothOffset.y);
            for (Body body : bodies) body.drawRelative((Graphics2D) rasteredView.getGraphics(), transformation);
            g.drawImage(rasteredView, -smoothOffset.x - 2*Application.BLOCK_SIZE, -smoothOffset.y - 2*Application.BLOCK_SIZE, rasteredView.getWidth(), rasteredView.getHeight(), null);

//            final BufferedImage image = map.getImage();
//            BufferedImage glass = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_4BYTE_ABGR);
//            MaterialVisuals.redraw(map, rasteredView);
//            Graphics2D g = (Graphics2D) graphics;
////            g.drawImage(rasteredView, -smoothOffset.x, -smoothOffset.y, rasteredView.getWidth(), rasteredView.getHeight(), null);
//
////            for (Body body : bodies) body.drawRelative(g, transformation);
////            for (GraphicComponent component : objects) component.drawRelative(g, transformation);
//            for (Body body : bodies) body.draw((Graphics2D) glass.getGraphics());
//
//            rasteredView.getGraphics().drawImage(glass, 0, 0, rasteredView.getWidth(), rasteredView.getHeight(), null);
//            g.drawImage(rasteredView, 0, 0, getWidth(), getHeight(), null);

//             reset view
            g.setTransform(new AffineTransform());
        }
    }

    private void recalculateGraphicWindowLayout() {
        panelViewDimensions = getSize();
        if (panelViewDimensions.width == 0)
            panelViewDimensions = new Dimension(Application.BLOCK_SIZE, Application.BLOCK_SIZE);
        tileViewDimensions = new Dimension(
                panelViewDimensions.width / Application.BLOCK_SIZE,
                panelViewDimensions.height / Application.BLOCK_SIZE);
        // even to avoid problems with centering when window is made larger
        tileViewDimensions.width += 4;
        tileViewDimensions.height += 4;
        if (tileViewDimensions.width % 2 != 0) tileViewDimensions.width++;
        if (tileViewDimensions.height % 2 != 0) tileViewDimensions.height++;
        rasteredView = new BufferedImage(
                (tileViewDimensions.width) * Application.BLOCK_SIZE,
                (tileViewDimensions.height) * Application.BLOCK_SIZE,
                BufferedImage.TYPE_INT_RGB);
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
                case CHAT:
                    main.windows.ChatInputPanel.getInstance().getField().grabFocus();
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
                Point p = new Point(viewTilePos.x * Application.BLOCK_SIZE + e.getX(), viewTilePos.y * Application.BLOCK_SIZE + e.getY());
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
        recalculateGraphicWindowLayout();
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

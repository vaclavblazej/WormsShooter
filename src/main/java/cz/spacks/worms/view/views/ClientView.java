package cz.spacks.worms.view.views;

import cz.spacks.worms.controller.comunication.client.ClientCommunication;
import cz.spacks.worms.controller.comunication.client.actions.impl.MoveAction;
import cz.spacks.worms.view.component.InventoryViewModel;
import cz.spacks.worms.view.windows.InventoryPanel;
import cz.spacks.worms.controller.Settings;
import cz.spacks.worms.model.objects.Body;
import cz.spacks.worms.model.objects.GraphicComponent;
import cz.spacks.worms.model.objects.MoveEnum;
import cz.spacks.worms.model.objects.items.ItemBlueprint;
import cz.spacks.worms.model.objects.items.itemActions.ItemAction;
import cz.spacks.worms.model.Controls;
import cz.spacks.worms.controller.properties.ControlsEnum;
import cz.spacks.worms.model.MapModel;
import cz.spacks.worms.model.objects.Model;
import cz.spacks.worms.view.defaults.DefaultComponentListener;
import cz.spacks.worms.view.defaults.DefaultKeyListener;
import cz.spacks.worms.view.defaults.DefaultMouseListener;
import cz.spacks.worms.view.defaults.DefaultMouseMotionListener;
import cz.spacks.worms.controller.materials.MaterialVisuals;
import cz.spacks.worms.view.windows.ChatInputPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.RasterFormatException;
import java.util.EnumSet;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 */
public class ClientView extends AbstractView implements
        ActionListener,
        DefaultKeyListener,
        DefaultMouseMotionListener,
        DefaultMouseListener,
        DefaultComponentListener {

    private static final Logger logger = Logger.getLogger(ClientView.class.getName());

    private static ClientView instance;

    public static ClientView getInstance() {
        if (instance == null) instance = new ClientView();
        return instance;
    }

    private MinimapView minimapView;
    private InventoryPanel inventory;
    private Dimension tileViewDimensions;
    private Dimension panelViewDimensions;
    private Body body;
    private EnumSet<ControlsEnum> controlSet;
    private MapModel currentView;
    private BufferedImage rasteredView;
    private Point viewTileStartPos;
    private final Point viewRealPos;
    private AffineTransform transformation;         // Defines view position and size. Is inverted already.
    private Controls controls;
    private Point mouse;

    private ClientView() {
        super(Settings.BLOCK_SIZE);
        map = new MapModel(new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB));
//        recalculateGraphicWindowLayout();
        currentView = null;
        viewTileStartPos = new Point();
        viewRealPos = new Point();
        controlSet = EnumSet.noneOf(ControlsEnum.class);
        mouse = new Point();
        transformation = new AffineTransform();
        // todo ratio with scale

        minimapView = MinimapView.getInstance();
        minimapView.setVisible(false);
        inventory = new InventoryPanel();
        inventory.setVisible(false);
        recalculateGraphicWindowLayout();
    }

    @Override
    public void setModel(Model model) {
        super.setModel(model);
        minimapView.setModel(model);
    }

    public void setMyView(Body body) {
        this.body = body;
    }

    public Body getMyViewBody() {
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
        if (body != null) {
            // view point
            Point bodyPosition = body.getPosition();
            viewRealPos.x = bodyPosition.x - panelViewDimensions.width / 2;
            viewRealPos.y = bodyPosition.y - panelViewDimensions.height / 2;

            // map edges
            int maxX = map.getWidth() * Settings.BLOCK_SIZE - panelViewDimensions.width - 1;
            if (viewRealPos.x < 0) {
                viewRealPos.x = 0;
            } else if (viewRealPos.x > maxX) {
                viewRealPos.x = maxX;
            }
            int maxY = map.getHeight() * Settings.BLOCK_SIZE - panelViewDimensions.height - 1;
            if (viewRealPos.y < 0) {
                viewRealPos.y = 0;
            } else if (viewRealPos.y > maxY) {
                viewRealPos.y = maxY;
            }
            viewTileStartPos.x = viewRealPos.x / Settings.BLOCK_SIZE;
            viewTileStartPos.y = viewRealPos.y / Settings.BLOCK_SIZE;
            smoothOffset.x = viewRealPos.x % Settings.BLOCK_SIZE;
            smoothOffset.y = viewRealPos.y % Settings.BLOCK_SIZE;

            // todo more effective
            tileViewDimensions = new Dimension(
                    (panelViewDimensions.width + smoothOffset.x) / Settings.BLOCK_SIZE + 1,
                    (panelViewDimensions.height + smoothOffset.y) / Settings.BLOCK_SIZE + 1);
            rasteredView = new BufferedImage(
                    tileViewDimensions.width * Settings.BLOCK_SIZE,
                    tileViewDimensions.height * Settings.BLOCK_SIZE,
                    BufferedImage.TYPE_INT_RGB);

            // print to screen
            try {
                currentView = map.getSubmap(viewTileStartPos, tileViewDimensions);
            } catch (RasterFormatException | ArrayIndexOutOfBoundsException ex) {
                logger.log(Level.SEVERE, null, ex);
            }
            MaterialVisuals.redraw(currentView, rasteredView);
            Graphics2D g = (Graphics2D) graphics;
            transformation.setToTranslation(-viewRealPos.x + smoothOffset.x, -viewRealPos.y + smoothOffset.y);
            final Graphics rasteredViewGraphics = rasteredView.getGraphics();
            for (Body body : bodies) body.drawRelative((Graphics2D) rasteredViewGraphics, transformation);
            for (GraphicComponent gc : objects) gc.drawRelative((Graphics2D) rasteredViewGraphics, transformation);
            g.drawImage(rasteredView, -smoothOffset.x, -smoothOffset.y, rasteredView.getWidth(), rasteredView.getHeight(), null);

//            final BufferedImage image = map.getImage();
//            BufferedImage glass = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_4BYTE_ABGR);
//            MaterialVisuals.redraw(map, rasteredView);
//            Graphics2D g = (Graphics2D) graphics;
////            g.drawImage(rasteredView, -smoothOffset.x, -smoothOffset.y, rasteredView.getWidth(), rasteredView.getHeight(), null);
//
////            for (Body body : bodies) body.drawRelative(g, transformation);
////            for (GraphicComponent component : cz.spacks.worms.objects) component.drawRelative(g, transformation);
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
            panelViewDimensions = new Dimension(Settings.BLOCK_SIZE, Settings.BLOCK_SIZE);
        tileViewDimensions = new Dimension(
                (panelViewDimensions.width) / Settings.BLOCK_SIZE,
                (panelViewDimensions.height) / Settings.BLOCK_SIZE);
        rasteredView = new BufferedImage(
                tileViewDimensions.width * Settings.BLOCK_SIZE,
                tileViewDimensions.height * Settings.BLOCK_SIZE,
                BufferedImage.TYPE_INT_RGB);
    }

    @Override
    public void keyPressed(KeyEvent ke) {
        int i = ke.getKeyCode();
//        System.out.println(i);
        ControlsEnum en = controls.get(i);
        if (en != null && controlSet.add(en)) {
            switch (en) {
                case JUMP:
                    ClientCommunication.getInstance().send(new MoveAction(MoveEnum.JUMP));
                    break;
                case UP:
                case DOWN:
                case LEFT:
                case RIGHT:
                    changeMovement();
                    break;
                case CHAT:
                    ChatInputPanel.getInstance().getField().grabFocus();
                    break;
                case MAP_TOGGLE:
                    minimapView.setVisible(!minimapView.isVisible());
                    break;
                case INVENTORY_TOGGLE:
                    inventory.updateInventoryModel(new InventoryViewModel(getMyViewBody()));
                    inventory.updateCraftingModel(getItemFactory().getRecipes());
                    inventory.setVisible(!inventory.isVisible());
                    break;
                case INTERACT:
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
                case UP:
                case DOWN:
                case LEFT:
                case RIGHT:
                    changeMovement();
                    break;
            }
        }
    }

    public void changeMovement() {
        int hMov = 0;
        int vMov = 0;
        if (controlSet.contains(ControlsEnum.UP)) vMov++;
        if (controlSet.contains(ControlsEnum.DOWN)) vMov--;
        if (controlSet.contains(ControlsEnum.LEFT)) hMov--;
        if (controlSet.contains(ControlsEnum.RIGHT)) hMov++;
        switch (hMov) {
            case 1:
                ClientCommunication.getInstance().send(new MoveAction(MoveEnum.RIGHT));
                break;
            case 0:
                ClientCommunication.getInstance().send(new MoveAction(MoveEnum.HSTOP));
                break;
            case -1:
                ClientCommunication.getInstance().send(new MoveAction(MoveEnum.LEFT));
                break;
        }
        switch (vMov) {
            case 1:
                ClientCommunication.getInstance().send(new MoveAction(MoveEnum.UP));
                break;
            case 0:
                ClientCommunication.getInstance().send(new MoveAction(MoveEnum.VSTOP));
                break;
            case -1:
                ClientCommunication.getInstance().send(new MoveAction(MoveEnum.DOWN));
                break;
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        mouse.x = e.getX();
        mouse.y = e.getY();
        switch (e.getButton()) {
            case MouseEvent.BUTTON1:
                Point p = new Point(viewRealPos.x + e.getX(), viewRealPos.y + e.getY());
                ItemBlueprint heldItem = getMyViewBody().getHeldItem();
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
    public void componentResized(ComponentEvent e) {
        recalculateGraphicWindowLayout();
    }

    public MinimapView getMinimapView() {
        return minimapView;
    }

    public JPanel getInventory() {
        return inventory;
    }
}

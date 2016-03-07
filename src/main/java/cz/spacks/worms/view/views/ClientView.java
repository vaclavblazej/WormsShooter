package cz.spacks.worms.view.views;

import cz.spacks.worms.controller.Settings;
import cz.spacks.worms.controller.materials.MaterialVisuals;
import cz.spacks.worms.controller.properties.ControlsEnum;
import cz.spacks.worms.controller.services.WorldService;
import cz.spacks.worms.model.Controls;
import cz.spacks.worms.model.map.MapModel;
import cz.spacks.worms.model.objects.Body;
import cz.spacks.worms.model.objects.MoveEnum;
import cz.spacks.worms.model.map.WorldModel;
import cz.spacks.worms.model.objects.items.ItemBlueprint;
import cz.spacks.worms.model.objects.items.itemActions.ItemAction;
import cz.spacks.worms.view.component.FocusGrabber;
import cz.spacks.worms.view.defaults.DefaultComponentListener;
import cz.spacks.worms.view.defaults.DefaultKeyListener;
import cz.spacks.worms.view.defaults.DefaultMouseListener;
import cz.spacks.worms.view.defaults.DefaultMouseMotionListener;
import cz.spacks.worms.view.windows.InventoryPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
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

    private MinimapView minimapView;
    private InventoryPanel inventory;
    private Dimension tileViewDimensions;
    private Dimension panelViewDimensions;
    private Body body;
    private EnumSet<ControlsEnum> controlSet;
    private final Point viewRealPos;
    private AffineTransform transformation;         // Defines worldService position and size. Is inverted already.
    private Controls controls;
    private Point mouse;
    private FocusGrabber chatFocusGrabber = FocusGrabber.NULL;

    private MaterialVisuals materialVisuals;
    private Timer timer = new Timer(1000 / 40, this);

    public ClientView() {

        viewTileStartPos = new Point();
        viewRealPos = new Point();
        controlSet = EnumSet.noneOf(ControlsEnum.class);
        mouse = new Point();
        transformation = new AffineTransform();
        // todo ratio with scale

        minimapView = new MinimapView();
        minimapView.setVisible(false);
        inventory = new InventoryPanel();
        inventory.setVisible(false);
        inventory.setFocusGrabber(this);
        recalculateGraphicWindowLayout();
        materialVisuals = new MaterialVisuals();

        WorldService worldService = new WorldService();
        setWorldService(worldService);

        controls = Settings.getInstance().getControls();
        addMouseListener(this);
        addMouseMotionListener(this);
        addKeyListener(this);

        timer.start();
    }

    @Override
    public void setWorldService(WorldService worldService) {
        super.setWorldService(worldService);
        minimapView.setWorldService(worldService);
    }

    @Override
    public void setWorldModel(WorldModel worldModel) {
        super.setWorldModel(worldModel);
        minimapView.setWorldModel(worldModel);
    }

    public void setMyView(Body body) {
        this.body = body;
        inventory.setWorldService(worldService);
        inventory.setInventory(body);
    }

    private BufferedImage rasteredView = null;
    private Point viewTileStartPos = new Point();
    private Point lastViewTileStartPos = new Point();

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        Point smoothOffset = new Point(0, 0);
        if (body != null) {
            // worldService point
            Point bodyPosition = body.getPosition();
            viewRealPos.x = bodyPosition.x - panelViewDimensions.width / 2;
            viewRealPos.y = bodyPosition.y - panelViewDimensions.height / 2;

            // map edges
            int maxX = mapModelCache.getDimensions().width * Settings.BLOCK_SIZE - panelViewDimensions.width - 1;
            if (viewRealPos.x < 0) {
                viewRealPos.x = 0;
            } else if (viewRealPos.x > maxX) {
                viewRealPos.x = maxX;
            }
            int maxY = mapModelCache.getDimensions().height * Settings.BLOCK_SIZE - panelViewDimensions.height - 1;
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
//            if(rasteredView == null) {
            rasteredView = new BufferedImage(
                    tileViewDimensions.width * Settings.BLOCK_SIZE,
                    tileViewDimensions.height * Settings.BLOCK_SIZE,
                    BufferedImage.TYPE_INT_RGB);
//            }

            // print to screen
            try {
                BufferedImage currentView = mapViewModel.getSubimage(viewTileStartPos, tileViewDimensions);
                materialVisuals.redraw(currentView, rasteredView);
            } catch (RasterFormatException | ArrayIndexOutOfBoundsException ex) {
                logger.log(Level.SEVERE, null, ex);
            }
            Graphics2D g = (Graphics2D) graphics;
            transformation.setToTranslation(-viewRealPos.x + smoothOffset.x, -viewRealPos.y + smoothOffset.y);
            final Graphics rasteredViewGraphics = rasteredView.getGraphics();
            for (Body body : worldModelCache.getBodies())
                body.drawRelative((Graphics2D) rasteredViewGraphics, transformation);
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

//             reset worldService
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
                    worldService.move(MoveEnum.JUMP);
                    break;
                case UP:
                case DOWN:
                case LEFT:
                case RIGHT:
                    changeMovement();
                    break;
                case CHAT:
                    chatFocusGrabber.focus();
                    break;
                case MAP_TOGGLE:
                    minimapView.setVisible(!minimapView.isVisible());
                    break;
                case INVENTORY_TOGGLE:
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
                worldService.move(MoveEnum.RIGHT);
                break;
            case 0:
                worldService.move(MoveEnum.HSTOP);
                break;
            case -1:
                worldService.move(MoveEnum.LEFT);
                break;
        }
        switch (vMov) {
            case 1:
                worldService.move(MoveEnum.UP);
                break;
            case 0:
                worldService.move(MoveEnum.VSTOP);
                break;
            case -1:
                worldService.move(MoveEnum.DOWN);
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
                ItemBlueprint heldItem = body.getHeldItem();
                if (heldItem != null) {
                    ItemAction action = heldItem.getAction();
                    if (action != null) {
                        worldService.itemAction(action, p);
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

    public InventoryPanel getInventory() {
        return inventory;
    }

    public void setChatFocusGrabber(FocusGrabber chatFocusGrabber) {
        this.chatFocusGrabber = chatFocusGrabber;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        this.repaint();
    }
}

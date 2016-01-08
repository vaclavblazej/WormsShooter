package cz.spacks.worms.view.server;

import cz.spacks.worms.controller.materials.MaterialModel;
import cz.spacks.worms.model.objects.Body;
import cz.spacks.worms.model.objects.GraphicComponent;
import cz.spacks.worms.model.objects.items.*;
import cz.spacks.worms.model.objects.items.itemActions.ItemActionMine;
import cz.spacks.worms.model.objects.items.itemActions.ItemActionShoot;
import cz.spacks.worms.controller.AbstractView;
import cz.spacks.worms.model.MapClass;
import cz.spacks.worms.model.communication.Model;
import cz.spacks.worms.controller.services.SpriteLoader;
import cz.spacks.worms.model.objects.Crafting;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.logging.Logger;

/**
 *
 */
public class ServerView extends AbstractView implements ComponentListener {


    private static final Logger logger = Logger.getLogger(ServerView.class.getName());

    private static ServerView instance;

    public static ServerView getInstance() {
        if (instance == null) instance = new ServerView();
        return instance;
    }

    private BufferedImage rasteredView;

    private ServerView() {
        super(1);
        SpriteLoader.loadSprite("Map");
        SpriteLoader.set(150, 100);
        map = new MapClass(SpriteLoader.getSprite().getFrame(), this);
        model = new Model(map, new HashMap<>(20), objects, createItems());
        materialModel = new MaterialModel(this);
        createRecipes();

        SwingUtilities.invokeLater(this::recalculateGraphicWindowLayout);
        SwingUtilities.invokeLater(this::init);
    }

    private ItemFactory createItems() {
        SpriteLoader.loadSprite("Items");
        SpriteLoader.set(4, 4);
        ItemFactory itemFactory = new ItemFactory();
        itemFactory.addItem(ItemEnum.METAL, new ItemBlueprint("Metal", true, 1, 1, ItemCategory.MATERIAL, SpriteLoader.getRawSprite(4, 4), null));
        itemFactory.addItem(ItemEnum.GUN_POWDER, new ItemBlueprint("Gun powder", true, 1, 1, ItemCategory.MATERIAL, SpriteLoader.getRawSprite(4, 4), null));
        itemFactory.addItem(ItemEnum.BULLET, new ItemBlueprint("Bullet", false, 1, 1, ItemCategory.TOOL, SpriteLoader.getRawSprite(4, 4), null));
        itemFactory.addItem(ItemEnum.HANDGUN, new ItemBlueprint("Gun", true, 2, 2, ItemCategory.TOOL, SpriteLoader.getRawSprite(4, 4), new ItemActionShoot()));
        itemFactory.addItem(ItemEnum.HANDGUN_MAGAZINE, new ItemBlueprint("Magazine", false, 1, 2, ItemCategory.TOOL, SpriteLoader.getRawSprite(4, 4), null));
        itemFactory.addItem(ItemEnum.HANDGUN_BARREL, new ItemBlueprint("Barrel", false, 2, 1, ItemCategory.TOOL, SpriteLoader.getRawSprite(4, 4), null));
        itemFactory.addItem(ItemEnum.HANDGUN_MECHANISM, new ItemBlueprint("Mechanism", false, 1, 1, ItemCategory.TOOL, SpriteLoader.getRawSprite(4, 4), null));
        itemFactory.addItem(ItemEnum.HANDGUN_HANDLE, new ItemBlueprint("Handle", false, 2, 2, ItemCategory.TOOL, SpriteLoader.getRawSprite(4, 4), null));
        itemFactory.addItem(ItemEnum.HANDGUN_OPTICS, new ItemBlueprint("Optics", true, 2, 1, ItemCategory.TOOL, SpriteLoader.getRawSprite(4, 4), null));
        itemFactory.addItem(ItemEnum.SHOVEL, new ItemBlueprint("Shovel", true, 1, 4, ItemCategory.TOOL, SpriteLoader.getRawSprite(4, 4), new ItemActionMine()));
        return itemFactory;
    }

    private void createRecipes() {
        ItemFactory fac = getItemFactory();
        Crafting rec = getRecipes();
        Recipe r = new Recipe("Free gun");
        r.addProduct(fac.getBlueprint(ItemEnum.HANDGUN), 1);
        rec.addRecipe(r);
        r = new Recipe("Free shovel");
        r.addProduct(fac.getBlueprint(ItemEnum.SHOVEL), 1);
        rec.addRecipe(r);
        r = new Recipe("Free bullet");
        r.addProduct(fac.getBlueprint(ItemEnum.BULLET), 10);
        rec.addRecipe(r);
        r = new Recipe("Gun assemble");
        r.addIngredient(fac.getBlueprint(ItemEnum.HANDGUN_BARREL), 1);
        r.addIngredient(fac.getBlueprint(ItemEnum.HANDGUN_HANDLE), 1);
        r.addIngredient(fac.getBlueprint(ItemEnum.HANDGUN_MECHANISM), 1);
        r.addProduct(fac.getBlueprint(ItemEnum.HANDGUN), 1);
        rec.addRecipe(r);
        r = new Recipe("Gun disassemble");
        r.addIngredient(fac.getBlueprint(ItemEnum.HANDGUN), 1);
        r.addProduct(fac.getBlueprint(ItemEnum.HANDGUN_BARREL), 1);
        r.addProduct(fac.getBlueprint(ItemEnum.HANDGUN_HANDLE), 1);
        r.addProduct(fac.getBlueprint(ItemEnum.HANDGUN_MECHANISM), 1);
        rec.addRecipe(r);
        r = new Recipe("Gun barrel");
        r.addIngredient(fac.getBlueprint(ItemEnum.METAL), 4);
        r.addProduct(fac.getBlueprint(ItemEnum.HANDGUN_BARREL), 1);
        rec.addRecipe(r);
        r = new Recipe("Gun handle");
        r.addIngredient(fac.getBlueprint(ItemEnum.METAL), 8);
        r.addProduct(fac.getBlueprint(ItemEnum.HANDGUN_HANDLE), 1);
        rec.addRecipe(r);
        r = new Recipe("Gun mechanism");
        r.addIngredient(fac.getBlueprint(ItemEnum.METAL), 4);
        r.addProduct(fac.getBlueprint(ItemEnum.HANDGUN_MECHANISM), 1);
        rec.addRecipe(r);
        r = new Recipe("Bullet");
        r.addIngredient(fac.getBlueprint(ItemEnum.METAL), 1);
        r.addIngredient(fac.getBlueprint(ItemEnum.GUN_POWDER), 1);
        r.addProduct(fac.getBlueprint(ItemEnum.BULLET), 1);
        rec.addRecipe(r);
    }

    public void save() {
        SpriteLoader.saveSprite("Map", map.getImage());
    }

    @Override
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        Graphics2D g = (Graphics2D) graphics;
        final BufferedImage image = map.getImage();
        final BufferedImage glass = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_4BYTE_ABGR);
        final Graphics2D imgGraphics = (Graphics2D) glass.getGraphics();
        for (Body b : bodies) b.draw(imgGraphics);
        for (GraphicComponent c : objects) c.draw(imgGraphics);
        final Graphics raster = rasteredView.getGraphics();
        raster.drawImage(image, 0, 0, image.getWidth(), image.getHeight(), null);
        raster.drawImage(glass, 0, 0, glass.getWidth(), glass.getHeight(), null);
        g.drawImage(rasteredView, 0, 0, getWidth(), getHeight(), null);
    }


    private void recalculateGraphicWindowLayout() {
        final Dimension dimension = getSize();
        rasteredView = new BufferedImage(dimension.width, dimension.height, BufferedImage.TYPE_INT_RGB);
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

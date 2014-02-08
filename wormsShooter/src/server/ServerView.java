package server;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.HashMap;
import javax.swing.SwingUtilities;
import objects.Body;
import objects.GraphicComponent;
import objects.items.Crafting;
import objects.items.ItemBlueprint;
import objects.items.ItemEnum;
import objects.items.ItemFactory;
import objects.items.Recipe;
import objects.items.itemActions.ItemActionMine;
import objects.items.itemActions.ItemActionShoot;
import spritesheets.SpriteLoader;
import utilities.AbstractView;
import utilities.MapClass;
import utilities.communication.Model;
import utilities.materials.Material;

/**
 *
 * @author Skarab
 */
public class ServerView extends AbstractView {

    private static ServerView instance;

    public static ServerView getInstance() {
        if (instance == null) {
            instance = new ServerView();
        }
        return instance;
    }

    private ServerView() {
        super(400, 300, 1);
        SpriteLoader.loadSprite("Map");
        SpriteLoader.set(400, 300);
        map = new MapClass(SpriteLoader.getSprite().getFrame(), this);
        model = new Model(map,
                new HashMap<Integer, Body>(20),
                objects,
                createItems(),
                ServerComService.getInstance().getCounter());
        material = new Material(this);
        createReceipes();

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                init();
            }
        });
    }

    private ItemFactory createItems() {
        SpriteLoader.loadSprite("Materials");
        SpriteLoader.set(16, 16);
        ItemFactory itemFactory = new ItemFactory();
        itemFactory.addItem(ItemEnum.METAL, new ItemBlueprint("Metal", true, 1, 1, SpriteLoader.getRawSprite(4, 4), null));
        itemFactory.addItem(ItemEnum.GUN_POWDER, new ItemBlueprint("Gun powder", true, 1, 1, SpriteLoader.getRawSprite(4, 4), null));
        itemFactory.addItem(ItemEnum.BULLET, new ItemBlueprint("Bullet", false, 1, 1, SpriteLoader.getRawSprite(4, 4), null));
        itemFactory.addItem(ItemEnum.HANDGUN, new ItemBlueprint("Gun", true, 2, 2, SpriteLoader.getRawSprite(4, 4), new ItemActionShoot()));
        itemFactory.addItem(ItemEnum.HANDGUN_MAGAZINE, new ItemBlueprint("Magazine", false, 1, 2, SpriteLoader.getRawSprite(4, 4), null));
        itemFactory.addItem(ItemEnum.HANDGUN_BARREL, new ItemBlueprint("Barrel", false, 2, 1, SpriteLoader.getRawSprite(4, 4), null));
        itemFactory.addItem(ItemEnum.HANDGUN_MECHANISM, new ItemBlueprint("Mechanism", false, 1, 1, SpriteLoader.getRawSprite(4, 4), null));
        itemFactory.addItem(ItemEnum.HANDGUN_HANDLE, new ItemBlueprint("Handle", false, 2, 2, SpriteLoader.getRawSprite(4, 4), null));
        itemFactory.addItem(ItemEnum.HANDGUN_OPTICS, new ItemBlueprint("Optics", true, 2, 1, SpriteLoader.getRawSprite(4, 4), null));
        itemFactory.addItem(ItemEnum.SHOVEL, new ItemBlueprint("Shovel", true, 1, 4, SpriteLoader.getRawSprite(4, 4), new ItemActionMine()));
        return itemFactory;
    }

    private void createReceipes() {
        ItemFactory fac = getItemFactory();
        Crafting rec = getRecipes();
        Recipe r = new Recipe("Gun assemble");
        r.addIngredient(fac.getBlueprint(ItemEnum.HANDGUN_BARREL), 1);
        r.addIngredient(fac.getBlueprint(ItemEnum.HANDGUN_HANDLE), 1);
        r.addIngredient(fac.getBlueprint(ItemEnum.HANDGUN_MECHANISM), 1);
        r.addProduct(fac.getBlueprint(ItemEnum.HANDGUN), 1);
        rec.addReceipe(r);
        r = new Recipe("Gun disassemble");
        r.addIngredient(fac.getBlueprint(ItemEnum.HANDGUN), 1);
        r.addProduct(fac.getBlueprint(ItemEnum.HANDGUN_BARREL), 1);
        r.addProduct(fac.getBlueprint(ItemEnum.HANDGUN_HANDLE), 1);
        r.addProduct(fac.getBlueprint(ItemEnum.HANDGUN_MECHANISM), 1);
        rec.addReceipe(r);
        r = new Recipe("Gun barrel");
        r.addIngredient(fac.getBlueprint(ItemEnum.METAL), 4);
        r.addProduct(fac.getBlueprint(ItemEnum.HANDGUN_BARREL), 1);
        rec.addReceipe(r);
        r = new Recipe("Gun handle");
        r.addIngredient(fac.getBlueprint(ItemEnum.METAL), 8);
        r.addProduct(fac.getBlueprint(ItemEnum.HANDGUN_HANDLE), 1);
        rec.addReceipe(r);
        r = new Recipe("Gun mechanism");
        r.addIngredient(fac.getBlueprint(ItemEnum.METAL), 4);
        r.addProduct(fac.getBlueprint(ItemEnum.HANDGUN_MECHANISM), 1);
        rec.addReceipe(r);
        r = new Recipe("Bullet");
        r.addIngredient(fac.getBlueprint(ItemEnum.METAL), 1);
        r.addIngredient(fac.getBlueprint(ItemEnum.GUN_POWDER), 1);
        r.addProduct(fac.getBlueprint(ItemEnum.BULLET), 1);
        rec.addReceipe(r);
        r = new Recipe("Shovel");
        r.addProduct(fac.getBlueprint(ItemEnum.SHOVEL), 1);
        rec.addReceipe(r);
    }

    public void save() {
        SpriteLoader.saveSprite("Map", map.getImage());
    }

    @Override
    public void paint(Graphics graphics) {
        super.paint(graphics);
        Graphics2D g = (Graphics2D) graphics;
        g.drawImage(map.getImage(), 0, 0, getWidth(), getHeight(), null);
        for (Body b : bodies) {
            b.draw(g);
        }
        for (GraphicComponent c : objects) {
            c.draw(g);
        }
    }
}

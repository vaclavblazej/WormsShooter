package server;

import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.SwingUtilities;
import objects.TestBody;
import objects.items.Crafting;
import objects.items.ItemBlueprint;
import objects.items.ItemEnum;
import objects.items.ItemFactory;
import objects.items.Recipe;
import spritesheets.SpriteLoader;
import utilities.ViewInterface;
import utilities.communication.Model;

/**
 *
 * @author Skarab
 */
public class ServerView extends ViewInterface {

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
        map = SpriteLoader.getSprite().getFrame();
        model = new Model(map,
                ServerCommunication.getInstance().getControls(),
                createItems(),
                ServerComService.getInstance().getCounter());
        createReceipes();

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                init();
            }
        });
    }

    private ItemFactory createItems() {
        ItemFactory itemFactory = new ItemFactory();
        itemFactory.addItem(ItemEnum.METAL, new ItemBlueprint("Metal", 1, 1));
        itemFactory.addItem(ItemEnum.GUN_POWDER, new ItemBlueprint("Gun powder", 1, 1));
        itemFactory.addItem(ItemEnum.BULLET, new ItemBlueprint("Bullet", 1, 1));
        itemFactory.addItem(ItemEnum.HANDGUN, new ItemBlueprint("Gun", 2, 2));
        itemFactory.addItem(ItemEnum.HANDGUN_MAGAZINE, new ItemBlueprint("Magazine", 1, 2));
        itemFactory.addItem(ItemEnum.HANDGUN_BARREL, new ItemBlueprint("Barrel", 2, 1));
        itemFactory.addItem(ItemEnum.HANDGUN_MECHANISM, new ItemBlueprint("Mechanism", 1, 1));
        itemFactory.addItem(ItemEnum.HANDGUN_HANDLE, new ItemBlueprint("Handle", 2, 2));
        itemFactory.addItem(ItemEnum.HANDGUN_OPTICS, new ItemBlueprint("Optics", 2, 1));
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
    }

    public void save() {
        SpriteLoader.saveSprite("Map", map);
    }

    @Override
    public void paint(Graphics graphics) {
        super.paint(graphics);
        Graphics2D g = (Graphics2D) graphics;
        g.drawImage(map, 0, 0, getWidth(), getHeight(), null);
        for (TestBody b : bodies) {
            b.draw(g);
        }
    }
}

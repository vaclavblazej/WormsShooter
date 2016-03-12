package cz.spacks.worms.controller.services;

import cz.spacks.worms.controller.materials.MaterialModel;
import cz.spacks.worms.model.map.*;
import cz.spacks.worms.model.objects.Crafting;
import cz.spacks.worms.model.objects.items.*;
import cz.spacks.worms.model.objects.items.itemActions.ItemActionMine;
import cz.spacks.worms.model.objects.items.itemActions.ItemActionShoot;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;

public class DefaultWorldSetting {

    public WorldService getDefaultWorldService() {
        ItemFactory items = createItems();
        MaterialModel materialModel = new MaterialModel(items);

        final Dimension dimension = new Dimension(150, 100);
        MapModel mapModel = new MapModel(dimension);
        SpriteLoader.loadSprite("Map");
        SpriteLoader.set(dimension.width, dimension.height);
        final BufferedImage frame = SpriteLoader.getSprite().getFrame();
        for (int y = 0; y < frame.getHeight(); y++) {
            for (int x = 0; x < frame.getWidth(); x++) {
                final Color color = new Color(frame.getRGB(x, y));
                final Material material = materialModel.getMaterial(color);
                final ArrayList<MaterialAmount> materials = new ArrayList<>();
                materials.add(new MaterialAmount(material, 1));
                mapModel.addChunk(new Chunk(materials), new Point(x, y));
            }
        }

        WorldModel worldModel = new WorldModel(mapModel, new HashMap<>(), items);
        WorldService worldService = new WorldService(worldModel, materialModel);

        final Crafting recipes = createRecipes(items);
        worldModel.getFactory().setRecipes(recipes);
        return worldService;
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

    private Crafting createRecipes(ItemFactory factory) {
        Crafting rec = new Crafting();
        Recipe r = new Recipe("Free gun");
        r.addProduct(factory.getBlueprint(ItemEnum.HANDGUN), 1);
        rec.addRecipe(r);
        r = new Recipe("Free shovel");
        r.addProduct(factory.getBlueprint(ItemEnum.SHOVEL), 1);
        rec.addRecipe(r);
        r = new Recipe("Free bullet");
        r.addProduct(factory.getBlueprint(ItemEnum.BULLET), 10);
        rec.addRecipe(r);
        r = new Recipe("Gun assemble");
        r.addIngredient(factory.getBlueprint(ItemEnum.HANDGUN_BARREL), 1);
        r.addIngredient(factory.getBlueprint(ItemEnum.HANDGUN_HANDLE), 1);
        r.addIngredient(factory.getBlueprint(ItemEnum.HANDGUN_MECHANISM), 1);
        r.addProduct(factory.getBlueprint(ItemEnum.HANDGUN), 1);
        rec.addRecipe(r);
        r = new Recipe("Gun disassemble");
        r.addIngredient(factory.getBlueprint(ItemEnum.HANDGUN), 1);
        r.addProduct(factory.getBlueprint(ItemEnum.HANDGUN_BARREL), 1);
        r.addProduct(factory.getBlueprint(ItemEnum.HANDGUN_HANDLE), 1);
        r.addProduct(factory.getBlueprint(ItemEnum.HANDGUN_MECHANISM), 1);
        rec.addRecipe(r);
        r = new Recipe("Gun barrel");
        r.addIngredient(factory.getBlueprint(ItemEnum.METAL), 4);
        r.addProduct(factory.getBlueprint(ItemEnum.HANDGUN_BARREL), 1);
        rec.addRecipe(r);
        r = new Recipe("Gun handle");
        r.addIngredient(factory.getBlueprint(ItemEnum.METAL), 8);
        r.addProduct(factory.getBlueprint(ItemEnum.HANDGUN_HANDLE), 1);
        rec.addRecipe(r);
        r = new Recipe("Gun mechanism");
        r.addIngredient(factory.getBlueprint(ItemEnum.METAL), 4);
        r.addProduct(factory.getBlueprint(ItemEnum.HANDGUN_MECHANISM), 1);
        rec.addRecipe(r);
        r = new Recipe("Bullet");
        r.addIngredient(factory.getBlueprint(ItemEnum.METAL), 1);
        r.addIngredient(factory.getBlueprint(ItemEnum.GUN_POWDER), 1);
        r.addProduct(factory.getBlueprint(ItemEnum.BULLET), 1);
        rec.addRecipe(r);
        return rec;
    }
}

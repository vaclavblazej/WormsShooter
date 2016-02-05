package cz.spacks.worms.view.views;

import cz.spacks.worms.controller.materials.MaterialEnum;
import cz.spacks.worms.controller.materials.MaterialModel;
import cz.spacks.worms.controller.services.CacheReloader;
import cz.spacks.worms.controller.services.WorldService;
import cz.spacks.worms.model.map.MapModel;
import cz.spacks.worms.model.objects.Crafting;
import cz.spacks.worms.model.map.WorldModel;
import cz.spacks.worms.model.objects.items.ItemFactory;
import cz.spacks.worms.view.component.FocusGrabber;
import cz.spacks.worms.view.defaults.DefaultComponentListener;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

/**
 * Represents general worldService on the scene.
 */
public abstract class AbstractView extends JPanel implements
        DefaultComponentListener,
        FocusGrabber,
        CacheReloader {

    protected Random random;

    protected WorldService worldService;
    protected WorldModel worldModelCache; // from worldModel
    protected MaterialModel materialModelCache; // from worldService
    protected MapModel mapModelCache; // from worldModel

    /**
     * Creates worldService and calls reset method which is obligatory to implement.
     */
    public AbstractView() {
        random = new Random();
    }

    public WorldModel getModel() {
        return worldModelCache;
    }

    public void setWorldService(WorldService worldService) {
        this.worldService = worldService;
        worldService.addCacheReloader(this);
        reloadCache();
    }

    public WorldService getWorldService() {
        return worldService;
    }

    public void setWorldModel(WorldModel worldModel) {
        worldService.setWorldModel(worldModel);
        worldModelCache = worldService.getWorldModel();
    }

    public MaterialModel getMaterialModel() {
        return materialModelCache;
    }

    public ItemFactory getItemFactory() {
        return getModel().getFactory();
    }

    public Crafting getRecipes() {
        return getModel().getFactory().getRecipes();
    }

    public MaterialEnum change(int x, int y, MaterialEnum mat) {
        Graphics g = worldModelCache.getMap().getGraphics();
        MaterialEnum ret = materialModelCache.getMaterial(worldModelCache.getMap().getRGB(x, y));
        g.setColor(getMaterialModel().getColor(mat));
        g.drawLine(x, y, x, y);
        return ret;
    }

    public Color getPixel(int x, int y) {
        return worldService.getPixel(x, y);
    }

    public void focus() {
        this.grabFocus();
    }

    @Override
    public void reloadCache() {
        materialModelCache = worldService.getMaterialModel();
        worldModelCache = worldService.getWorldModel();
        if (worldModelCache != null) {
            mapModelCache = worldModelCache.getMap();
        }
    }
}

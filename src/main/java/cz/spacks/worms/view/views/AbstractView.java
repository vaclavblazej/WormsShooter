package cz.spacks.worms.view.views;

import cz.spacks.worms.controller.materials.MaterialEnum;
import cz.spacks.worms.controller.materials.MaterialModel;
import cz.spacks.worms.controller.services.CacheReloader;
import cz.spacks.worms.controller.services.WorldService;
import cz.spacks.worms.controller.services.WorldServiceListener;
import cz.spacks.worms.controller.services.controls.BodyView;
import cz.spacks.worms.model.map.Chunk;
import cz.spacks.worms.model.map.MapModel;
import cz.spacks.worms.model.map.WorldModel;
import cz.spacks.worms.model.objects.Body;
import cz.spacks.worms.model.objects.Crafting;
import cz.spacks.worms.model.objects.items.ItemFactory;
import cz.spacks.worms.view.MapViewModel;
import cz.spacks.worms.view.component.FocusGrabber;
import cz.spacks.worms.view.defaults.DefaultComponentListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Represents general worldService on the scene.
 */
public abstract class AbstractView extends JPanel implements
        WorldServiceListener,
        DefaultComponentListener,
        FocusGrabber,
        CacheReloader{

    protected Random random;

    protected WorldService worldService;
    protected MapViewModel mapViewModel;
    protected WorldModel worldModelCache; // from worldService
    protected MaterialModel materialModelCache; // from worldService
    protected MapModel mapModelCache; // from worldModel

    protected List<BodyView> bodyViews = new ArrayList<>();

    /**
     * Creates worldService and calls reset method which is obligatory to implement.
     */
    public AbstractView() {
        random = new Random();
        mapViewModel = new MapViewModel();
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
        worldService.getWorldModel().getMap().addListener(mapViewModel);
        worldModelCache = worldService.getWorldModel();
        worldService.addListener(this);
    }

    @Override
    public void addedBody(Body body) {
        addBodyView(new BodyView(body));
    }

    @Override
    public void removedBody(Body body) {
        // todo proper bodyView disposal
        System.out.println("Would remove body " + body.toString());
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
        Graphics g = mapViewModel.getGraphics();
        final Chunk chunk = worldModelCache.getMap().getChunk(new Point(x, y));
        MaterialEnum ret = materialModelCache.getMaterial(chunk);
        g.setColor(materialModelCache.getColor(mat));
        g.drawLine(x, y, x, y);
        return ret;
    }

    public Color getPixel(int x, int y) {
        return worldService.getPixel(x, y);
    }

    public List<BodyView> getBodyViews() {
        return bodyViews;
    }

    public void addBodyView(BodyView bodyView) {
        bodyViews.add(bodyView);
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

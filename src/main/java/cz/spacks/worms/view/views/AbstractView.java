package cz.spacks.worms.view.views;

import cz.spacks.worms.controller.materials.MaterialModel;
import cz.spacks.worms.controller.properties.CollisionState;
import cz.spacks.worms.controller.services.WorldService;
import cz.spacks.worms.model.MapModel;
import cz.spacks.worms.model.objects.Body;
import cz.spacks.worms.model.objects.Crafting;
import cz.spacks.worms.model.objects.items.ItemFactory;
import cz.spacks.worms.model.objects.WorldModel;
import cz.spacks.worms.view.component.FocusGrabber;
import cz.spacks.worms.view.defaults.DefaultComponentListener;
import cz.spacks.worms.controller.materials.MaterialEnum;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Represents general view on the scene.
 *
 *
 */
public abstract class AbstractView extends JPanel implements DefaultComponentListener, FocusGrabber {

    protected List<Body> bodies;
    protected Random random;

    protected WorldService worldService;
    protected WorldModel worldModelCache; // from worldModel
    protected MaterialModel materialModelCache; // from worldService
    protected MapModel mapModelCache; // from worldModel

    /**
     * Creates view and calls reset method which is obligatory to implement.
     */
    public AbstractView() {
        random = new Random();
    }

    public CollisionState check(int x, int y) {
        return worldService.getState(x, y);
    }

    public WorldModel getModel() {
        return worldModelCache;
    }

    public void setWorldService(WorldService worldService){
        this.worldService = worldService;
        materialModelCache = worldService.getMaterialModel();
        setWorldModel(worldService.getWorldModel());
    }

    public void setWorldModel(WorldModel worldModel) {
        worldService.setWorldModel(worldModel);
        worldModelCache = worldService.getWorldModel();
        bodies = new ArrayList<>(worldModel.getControls().values());
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

    public Body newBody() {
        Body b = new Body(2000, 1600);
        bodies.add(b);
        return b;
    }

    public Color getPixel(int x, int y) {
        return worldService.getPixel(x, y);
    }

    public void focus(){
        this.grabFocus();
    }
}

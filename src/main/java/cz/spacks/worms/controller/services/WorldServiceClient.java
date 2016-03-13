package cz.spacks.worms.controller.services;

import cz.spacks.worms.controller.services.controls.BodyControl;
import cz.spacks.worms.model.map.WorldModel;
import cz.spacks.worms.model.materials.MaterialModel;
import cz.spacks.worms.model.objects.Body;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class WorldServiceClient implements ActionListener {

    private WorldModel worldModel;
    private MaterialModel materialModel = MaterialModel.NULL;
    private Timer tickTimer = new Timer(40, this);
    private List<BodyControl> bodyControls = new ArrayList<>();

    private List<CacheReloader> cacheReloaderList = new ArrayList<>();

    public WorldServiceClient() {
    }

    public WorldServiceClient(WorldModel worldModel, MaterialModel materialModel) {
        this.worldModel = worldModel;
        this.materialModel = materialModel;
        notifyCacheRealoaders();
    }

    public void startTick() {
        tickTimer.start();
    }

    public void stopTick() {
        tickTimer.stop();
    }

    public void setWorldModel(WorldModel worldModel) {
        this.worldModel = worldModel;
    }

    public void setMaterialModel(MaterialModel materialModel) {
        this.materialModel = materialModel;
    }

    public WorldModel getWorldModel() {
        return worldModel;
    }

    public MaterialModel getMaterialModel() {
        return materialModel;
    }

//    public CollisionState getState(int x, int y) {
//        return materialModel.getState(getPixel(x, y));
//    }

//    public Color getPixel(int x, int y) {
//        return new Color(worldModel.getMap().getRGB(x, y));
//    }

    @Override
    public void actionPerformed(ActionEvent e) {
    }

    public Body newBody() {
        Body b = new Body(2000, 1600);
        bodyControls.add(new BodyControl(b));
        return b;
    }

    public void removeBody(Body body) {
        final Iterator<BodyControl> iterator = bodyControls.iterator();
        while (iterator.hasNext()) {
            final BodyControl bodyControl = iterator.next();
            if (bodyControl.getBody().equals(body)) {
                iterator.remove();
            }
        }
    }

    public void addCacheReloader(CacheReloader cacheReloader) {
        cacheReloaderList.add(cacheReloader);
    }

    public void notifyCacheRealoaders() {
        for (CacheReloader cacheReloader : cacheReloaderList) {
            cacheReloader.reloadCache();
        }
    }
}

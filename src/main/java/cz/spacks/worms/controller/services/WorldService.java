package cz.spacks.worms.controller.services;

import cz.spacks.worms.controller.materials.MaterialEnum;
import cz.spacks.worms.controller.materials.MaterialModel;
import cz.spacks.worms.controller.properties.CollisionState;
import cz.spacks.worms.model.objects.Body;
import cz.spacks.worms.model.objects.WorldModel;
import cz.spacks.worms.view.component.ChatLog;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class WorldService implements ActionListener {

    private WorldModel worldModel;
    private MaterialModel materialModel = MaterialModel.NULL;
    private Timer tickTimer;
    private ChatLog chatLog;

    private java.util.List<CacheReloader> cacheReloaderList = new ArrayList<>();

    public WorldService() {
        tickTimer = new Timer(40, this);
    }

    public WorldService(WorldModel worldModel, MaterialModel materialModel) {
        this.worldModel = worldModel;
        this.materialModel = materialModel;
        notifyCacheRealoaders();
        chatLog = new ChatLog();
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


    public CollisionState getState(int x, int y) {
        return materialModel.getState(getPixel(x, y));
    }

    public Color getPixel(int x, int y) {
        return new Color(worldModel.getMap().getRGB(x, y));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
//        for (Body b : worldModel) {
//            b.tick();
//        }
    }

    public Body newBody() {
        Body b = new Body(2000, 1600);
        worldModel.getBodies().add(b);
        return b;
    }

    public void removeBody(Body body) {
        worldModel.getBodies().remove(body);
    }

    public MaterialEnum change(int x, int y, MaterialEnum material) {
        Color color = materialModel.getColor(material);
        MaterialEnum removedMaterial = materialModel.getMaterial(color.getRGB());
        worldModel.getMap().getImage().setRGB(x, y, color.getRGB());
        return removedMaterial;
    }

    public void addCacheReloader(CacheReloader cacheReloader) {
        cacheReloaderList.add(cacheReloader);
    }

    public void notifyCacheRealoaders() {
        for (CacheReloader cacheReloader : cacheReloaderList) {
            cacheReloader.reloadCache();
        }
    }

    public void logChat(String log) {
        chatLog.log(log);
    }

    public ChatLog getChatLog() {
        return chatLog;
    }
}

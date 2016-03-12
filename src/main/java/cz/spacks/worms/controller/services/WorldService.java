package cz.spacks.worms.controller.services;

import cz.spacks.worms.controller.comunication.client.actions.impl.CraftAction;
import cz.spacks.worms.controller.events.WorldEvent;
import cz.spacks.worms.controller.materials.MaterialModel;
import cz.spacks.worms.controller.properties.CollisionState;
import cz.spacks.worms.controller.services.controls.BodyControl;
import cz.spacks.worms.model.ChatLog;
import cz.spacks.worms.model.map.WorldModel;
import cz.spacks.worms.model.objects.Body;
import cz.spacks.worms.model.objects.Inventory;
import cz.spacks.worms.model.objects.ItemsCount;
import cz.spacks.worms.model.objects.MoveEnum;
import cz.spacks.worms.model.objects.items.ItemEnum;
import cz.spacks.worms.model.objects.items.itemActions.ItemAction;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class WorldService implements ActionListener {

    private WorldModel worldModel;
    private MaterialModel materialModel = MaterialModel.NULL;
    private Timer tickTimer = new Timer(40, this);
    private ChatLog chatLog;
    private List<BodyControl> bodyControls = new ArrayList<>();

    private List<CacheReloader> cacheReloaderList = new ArrayList<>();
    private List<WorldServiceListener> listeners = new ArrayList<>();

    public WorldService() {
    }

    public WorldService(WorldModel worldModel, MaterialModel materialModel) {
        this.worldModel = worldModel;
        this.materialModel = materialModel;
        notifyCacheRealoaders();
        chatLog = new ChatLog();
    }

    public void addEvent(WorldEvent worldEvent) {
        worldModel.addEvent(worldEvent);
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
        // todo make more sane
        return new Color(worldModel.getMap().getChunk(new Point(x, y)).getMaterials().get(0).getMaterial().color.getRGB());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        for (BodyControl bodyControl : bodyControls) {
            bodyControl.tick(this);
        }
    }

    public Body newBody() {
        Body body = new Body(2000, 1600);
        bodyControls.add(new BodyControl(body));
        final Inventory inventory = body.getInventory();
        final ArrayList<ItemsCount> addedComponents = new ArrayList<>();
        addedComponents.add(new ItemsCount(worldModel.getFactory().getBlueprint(ItemEnum.SHOVEL), 2));
        inventory.addAll(addedComponents);
        for (WorldServiceListener listener : listeners) {
            listener.addedBody(body);
        }
        return body;
    }

    public void removeBody(Body body) {
        // todo remove control, but not the body
        for (WorldServiceListener listener : listeners) {
            listener.removedBody(body);
        }
        final Iterator<BodyControl> iterator = bodyControls.iterator();
        while (iterator.hasNext()) {
            final BodyControl bodyControl = iterator.next();
            if (bodyControl.getBody().equals(body)) {
                iterator.remove();
            }
        }
    }

    public void addListener(WorldServiceListener listener) {
        listeners.add(listener);
    }

    public List<BodyControl> getBodyControls() {
        return bodyControls;
    }
    //    todo
//    public MaterialEnum change(int x, int y, MaterialEnum material) {
//        Color color = materialModel.getColor(material);
//        MaterialEnum removedMaterial = materialModel.getMaterial(color.getRGB());
//        worldModel.getMap().getImage().setRGB(x, y, color.getRGB());
//        return removedMaterial;
//    }

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

    public void move(MoveEnum action) {
        final BodyControl bodyControl = bodyControls.get(0);
        if (bodyControl != null) {
            bodyControl.control(action);
        }
    }

    public void itemAction(ItemAction action, Point p) {
        action.action(p).perform();
    }

    public void action(CraftAction craftAction) {
        craftAction.perform();
    }

    public void craft(int recipeId) {
        action(new CraftAction(recipeId));
    }
}

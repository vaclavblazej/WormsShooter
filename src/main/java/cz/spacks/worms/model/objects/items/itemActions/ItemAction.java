package cz.spacks.worms.model.objects.items.itemActions;

import cz.spacks.worms.controller.services.WorldService;

import java.awt.*;

/**
 *
 */
public abstract class ItemAction {

    protected static WorldService worldService;

    public static void setWorldService(WorldService aView) {
        worldService = aView;
    }

    public abstract void action(Point point);
}

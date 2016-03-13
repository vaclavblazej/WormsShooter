package cz.spacks.worms.controller.events.itemActions;

import cz.spacks.worms.controller.Settings;
import cz.spacks.worms.controller.events.impl.DamageChunkEvent;
import cz.spacks.worms.model.objects.Body;

import java.awt.*;

/**
 *
 */
public class ItemActionMine extends ItemAction {

    @Override
    public void action(Point point) {
        Point blockPosition = new Point(point.x / Settings.BLOCK_SIZE, point.y / Settings.BLOCK_SIZE);
        Body body = worldService.getWorldModel().getControls().get(0);
        Point pos = body.getPosition();
        int distance = Math.abs(pos.x / Settings.BLOCK_SIZE - blockPosition.x) + Math.abs(pos.y / Settings.BLOCK_SIZE - blockPosition.y);
        if (distance < 6) {
            worldService.addEvent(new DamageChunkEvent(blockPosition));
        }
    }
}

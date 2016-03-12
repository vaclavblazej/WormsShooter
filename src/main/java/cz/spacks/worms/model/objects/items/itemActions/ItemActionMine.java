package cz.spacks.worms.model.objects.items.itemActions;

import cz.spacks.worms.controller.Settings;
import cz.spacks.worms.controller.events.impl.RemoveChunkEvent;
import cz.spacks.worms.controller.materials.MaterialEnum;
import cz.spacks.worms.model.map.Chunk;
import cz.spacks.worms.model.map.MapModel;
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
            System.out.println("hello MINING");
            MaterialEnum to = MaterialEnum.AIR;
            worldService.addEvent(new RemoveChunkEvent(blockPosition));
            final MapModel map = worldService.getWorldModel().getMap();
            final Chunk minedChunk = map.getChunk(blockPosition);
            final Chunk newChunk = worldService.getMaterialModel().getChunk(to);
            map.addChunk(newChunk, blockPosition);
            final MaterialEnum type = minedChunk.getMaterials().get(0).getMaterial().type; // todo
            body.getInventory().addAll(worldService.getMaterialModel().getComponents(type));
        }
    }
}

package cz.spacks.worms.model.objects.items.itemActions;

import cz.spacks.worms.model.objects.Body;

import java.awt.*;

/**
 *
 */
public class ItemActionShoot extends ItemAction {

    @Override
    public void action(Point point) {
        Body body = worldService.getWorldModel().getControls().get(0);
        Point pos = (Point) body.getPosition().clone();
        double rad = Math.atan2(point.y - pos.y, point.x - pos.x);
//        worldService.addObject(new Bullet(pos, rad, worldService)); // todo fix objects
//        serverCommunication.broadcast(new ShootServerAction(pos, rad));
    }
}

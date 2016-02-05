package cz.spacks.worms.controller.comunication.client.actions.impl;

import cz.spacks.worms.controller.comunication.client.actions.ActionClient;
import cz.spacks.worms.controller.comunication.server.actions.impl.MoveServerAction;
import cz.spacks.worms.model.objects.Body;
import cz.spacks.worms.model.objects.MoveEnum;

import java.awt.*;

/**
 *
 */
public class MoveAction extends ActionClient {

    private MoveEnum action;

    public MoveAction(MoveEnum action) {
        this.action = action;
    }

    @Override
    public void perform() {
        Body body = worldService.getWorldModel().getControls().get(id);
        if (body != null) {
            Point pos = body.getPosition();
            Point.Double vel = body.getVelocity();
            serverCommunication.broadcast(new MoveServerAction(id, pos, vel, action));
            body.control(action);
        }
    }
}

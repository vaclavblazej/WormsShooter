package client.actions.impl;

import client.actions.ActionClient;
import objects.Body;
import objects.MoveEnum;
import server.ServerCommunication;
import server.actions.impl.MoveServerAction;

import java.awt.*;

/**
 * @author Václav Blažej
 */
public class MoveAction extends ActionClient {

    private MoveEnum action;

    public MoveAction(MoveEnum action) {
        this.action = action;
    }

    @Override
    public void perform() {
        Body body = view.getModel().getControls().get(id);
        if (body != null) {
            Point pos = body.getPosition();
            Point.Double vel = body.getVelocity();
            ServerCommunication.getInstance().broadcast(new MoveServerAction(id, pos, vel, action));
            body.control(action);
        }
    }
}

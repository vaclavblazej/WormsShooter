package cz.spacks.worms.controller.comunication.server;

import cz.spacks.worms.controller.comunication.serialization.SerializableModel;
import cz.spacks.worms.controller.comunication.server.actions.ActionServer;
import cz.spacks.worms.controller.comunication.server.actions.impl.GetModelServerAction;
import cz.spacks.worms.controller.comunication.server.actions.impl.NewPlayerServerAction;
import cz.spacks.worms.controller.comunication.server.actions.impl.SetIdNewPlayerServerAction;
import cz.spacks.worms.controller.services.WorldService;
import cz.spacks.worms.model.objects.Body;
import spacks.communication.utilities.SAction;
import spacks.communication.utilities.SListener;

import java.util.Collection;
import java.util.Map;

/**
 *
 */
public abstract class ServerCommunication implements SListener {

    public abstract void broadcast(SAction action);

    public abstract void broadcastExceptOne(int leftOut, SAction action);

    public abstract void send(int id, ActionServer action);

    public abstract void sendToGroup(Collection<Integer> ids, SAction action);

    public abstract void disconnect(int id);

    private WorldService worldService;

    public void setWorldService(WorldService worldService) {
        this.worldService = worldService;
    }

    @Override
    public void connectionCreated(int id) {
        Body body = worldService.newBody();
        Map<Integer, Body> controls = worldService.getWorldModel().getControls();
        controls.put(id, body);
        send(id, new SetIdNewPlayerServerAction(id));
        send(id, new GetModelServerAction(new SerializableModel().serialize(worldService.getWorldModel())));
        broadcastExceptOne(id, new NewPlayerServerAction(id));
    }

    @Override
    public void connectionRemoved(int id) {
        Map<Integer, Body> controls = worldService.getWorldModel().getControls();
        worldService.removeBody(controls.get(id));
        controls.remove(id);
    }
}

package cz.spacks.worms.controller.comunication.server;

import cz.spacks.worms.controller.comunication.client.actions.ActionClient;
import cz.spacks.worms.controller.comunication.serialization.SerializableModel;
import cz.spacks.worms.model.objects.Body;
import cz.spacks.worms.view.server.ServerView;
import cz.spacks.worms.controller.comunication.server.actions.ActionServer;
import cz.spacks.worms.controller.comunication.server.actions.impl.GetModelServerAction;
import cz.spacks.worms.controller.comunication.server.actions.impl.NewPlayerServerAction;
import cz.spacks.worms.controller.comunication.server.actions.impl.SetIdNewPlayerServerAction;
import spacks.communication.utilities.SAction;
import spacks.communication.utilities.SListener;

import java.util.Collection;
import java.util.Map;

/**
 *
 */
public abstract class ServerCommunication implements SListener {

    private static ServerCommunication instance;

    public static ServerCommunication getInstance() {
        return instance;
    }

    public ServerCommunication() {
        ActionClient.setView(ServerView.getInstance());
        instance = this;
    }

    public abstract void broadcast(SAction action);

    public abstract void broadcastExceptOne(int leftOut, SAction action);

    public abstract void send(int id, ActionServer action);

    public abstract void sendToGroup(Collection<Integer> ids, SAction action);

    public abstract void disconnect(int id);

    @Override
    public void connectionCreated(int id) {
        final ServerView serverView = ServerView.getInstance();
        Body body = serverView.newBody();
        Map<Integer, Body> controls = serverView.getModel().getControls();
        controls.put(id, body);
        final ServerCommunication serverCommunication = ServerCommunication.getInstance();
        serverCommunication.send(id, new SetIdNewPlayerServerAction(id));
        serverCommunication.send(id, new GetModelServerAction(new SerializableModel().serialize(serverView.getModel())));
        serverCommunication.broadcastExceptOne(id, new NewPlayerServerAction(id));
    }

    @Override
    public void connectionRemoved(int id) {
        Map<Integer, Body> controls = ServerView.getInstance().getModel().getControls();
        ServerView.getInstance().removeBody(controls.get(id));
        controls.remove(id);
    }
}

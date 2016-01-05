package cz.spacks.worms.view.client;

import cz.spacks.worms.view.client.actions.ActionClient;
import cz.spacks.worms.view.client.actions.impl.GetModelAction;
import cz.spacks.worms.model.objects.Body;
import cz.spacks.worms.controller.PlayerInfo;

import java.util.Map;
import java.util.logging.Logger;

/**
 *
 */
public abstract class ClientCommunication {

    private static final Logger logger = Logger.getLogger(ClientCommunication.class.getName());

    private static ClientCommunication instance;

    public static ClientCommunication getInstance() {
        return instance;
    }

    public ClientCommunication() {
        instance = this;
    }

    private PlayerInfo info;

    public abstract void send(ActionClient packet) ;

    public void bindBody(int id, Body body) {
        Map<Integer, Body> controls = ClientView.getInstance().getModel().getControls();
        controls.put(id, body);
    }

    public void unbindBody(int id) {
        Map<Integer, Body> controls = ClientView.getInstance().getModel().getControls();
        ClientView.getInstance().removeBody(controls.get(id));
        controls.remove(id);
    }

    public void getModel() {
        logger.info("Client: getting model");
        send(new GetModelAction());
    }

    public PlayerInfo getInfo() {
        return info;
    }

    public void setInfo(PlayerInfo info) {
        this.info = info;
    }
}

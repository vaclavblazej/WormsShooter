package cz.spacks.worms.controller.comunication.client;

import cz.spacks.worms.controller.PlayerInfo;
import cz.spacks.worms.controller.comunication.client.actions.ActionClient;
import cz.spacks.worms.controller.comunication.client.actions.impl.GetModelAction;
import cz.spacks.worms.controller.services.WorldService;
import cz.spacks.worms.model.objects.Body;
import cz.spacks.worms.model.map.WorldModel;
import cz.spacks.worms.view.views.ClientView;

import java.util.Map;
import java.util.logging.Logger;

/**
 *
 */
public abstract class ClientCommunication {

    private static final Logger logger = Logger.getLogger(ClientCommunication.class.getName());

    private PlayerInfo info;
    protected WorldService worldService;
    protected ClientView clientView;

    public abstract void send(ActionClient packet);

    public void setWorldService(WorldService worldService) {
        this.worldService = worldService;
    }

    public void bindBody(int id, Body body) {
        Map<Integer, Body> controls = worldService.getWorldModel().getControls();
        controls.put(id, body);
    }

    public void unbindBody(int id) {
        Map<Integer, Body> controls = worldService.getWorldModel().getControls();
        worldService.removeBody(controls.get(id));
        controls.remove(id);
    }

    public void getModel() {
        logger.info("Client: getting model");
        send(new GetModelAction());
    }

    public PlayerInfo getInfo() {
        return info;
    }

    public void setClientView(ClientView clientView) {
        this.clientView = clientView;
    }

    public void setInfo(PlayerInfo info) {
        this.info = info;
        final WorldModel worldModel = worldService.getWorldModel();
        final Map<Integer, Body> controls = worldModel.getControls();
        clientView.setMyView(controls.get(info.getId()));
    }
}

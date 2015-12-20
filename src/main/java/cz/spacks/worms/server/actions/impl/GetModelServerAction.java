package cz.spacks.worms.server.actions.impl;

import cz.spacks.worms.client.ClientCommunication;
import cz.spacks.worms.client.ClientView;
import cz.spacks.worms.objects.Body;
import cz.spacks.worms.server.actions.ActionServer;
import cz.spacks.worms.utilities.communication.Model;
import cz.spacks.worms.utilities.communication.SerializableModel;

import java.util.Map;

/**
 *
 */
public class GetModelServerAction extends ActionServer {

    public SerializableModel model;

    public GetModelServerAction(SerializableModel model) {
        this.model = model;
    }

    @Override
    public void perform() {
        System.out.println("Client: model received");
        Model realModel = model.deserialize(view);
        view.setModel(realModel);
        final int id = ClientCommunication.getInstance().getInfo().getId();
        final Map<Integer, Body> controls = realModel.getControls();
        ClientView.getInstance().setMyView(controls.get(id));
    }
}

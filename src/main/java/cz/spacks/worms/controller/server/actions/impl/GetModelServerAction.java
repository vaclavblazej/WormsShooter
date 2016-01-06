package cz.spacks.worms.controller.server.actions.impl;

import cz.spacks.worms.controller.client.ClientCommunication;
import cz.spacks.worms.view.client.ClientView;
import cz.spacks.worms.model.objects.Body;
import cz.spacks.worms.controller.server.actions.ActionServer;
import cz.spacks.worms.controller.communication.Model;
import cz.spacks.worms.controller.communication.SerializableModel;

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

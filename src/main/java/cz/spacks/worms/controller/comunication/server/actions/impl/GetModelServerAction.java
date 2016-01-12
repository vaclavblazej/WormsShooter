package cz.spacks.worms.controller.comunication.server.actions.impl;

import cz.spacks.worms.controller.comunication.client.ClientCommunication;
import cz.spacks.worms.view.views.ClientView;
import cz.spacks.worms.model.objects.Body;
import cz.spacks.worms.controller.comunication.server.actions.ActionServer;
import cz.spacks.worms.model.objects.Model;
import cz.spacks.worms.controller.comunication.serialization.SerializableModel;

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
        Model realModel = model.deserialize();
        view.setModel(realModel);
        final int id = ClientCommunication.getInstance().getInfo().getId();
        final Map<Integer, Body> controls = realModel.getControls();
        ClientView.getInstance().setMyView(controls.get(id));
    }
}

package cz.spacks.worms.controller.comunication.server.actions.impl;

import cz.spacks.worms.controller.comunication.client.ClientCommunication;
import cz.spacks.worms.model.objects.Body;
import cz.spacks.worms.controller.comunication.server.actions.ActionServer;
import cz.spacks.worms.model.objects.WorldModel;
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
        WorldModel realWorldModel = model.deserialize();
        view.setWorldModel(realWorldModel);
        final int id = clientCommunication.getInfo().getId();
        final Map<Integer, Body> controls = realWorldModel.getControls();
        view.setMyView(controls.get(id));
    }
}

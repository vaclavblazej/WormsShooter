package cz.spacks.worms.controller.comunication.server.actions.impl;

import cz.spacks.worms.controller.comunication.serialization.SerializableModel;
import cz.spacks.worms.controller.comunication.server.actions.ActionServer;
import cz.spacks.worms.model.objects.WorldModel;

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
        worldService.setWorldModel(realWorldModel);
    }
}

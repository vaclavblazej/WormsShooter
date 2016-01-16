package cz.spacks.worms.controller.comunication.client.actions.impl;

import cz.spacks.worms.controller.comunication.client.actions.ActionClient;
import cz.spacks.worms.controller.comunication.serialization.SerializableModel;
import cz.spacks.worms.controller.comunication.server.ServerCommunication;
import cz.spacks.worms.controller.comunication.server.actions.impl.GetModelServerAction;

/**
 *
 */
public class GetModelAction extends ActionClient {

    @Override
    public void perform() {
        SerializableModel model = new SerializableModel().serialize(view.getModel());
        serverCommunication.send(id, new GetModelServerAction(model));
    }
}

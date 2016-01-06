package cz.spacks.worms.controller.client.actions.impl;

import cz.spacks.worms.controller.client.actions.ActionClient;
import cz.spacks.worms.controller.server.ServerCommunication;
import cz.spacks.worms.controller.server.actions.impl.GetModelServerAction;

/**
 *
 */
public class GetModelAction extends ActionClient {

    @Override
    public void perform() {
        ServerCommunication.getInstance().send(id, new GetModelServerAction(view.getModel().serialize()));
    }
}

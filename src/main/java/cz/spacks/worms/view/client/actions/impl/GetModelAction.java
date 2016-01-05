package cz.spacks.worms.view.client.actions.impl;

import cz.spacks.worms.view.client.actions.ActionClient;
import cz.spacks.worms.view.server.ServerCommunication;
import cz.spacks.worms.view.server.actions.impl.GetModelServerAction;

/**
 *
 */
public class GetModelAction extends ActionClient {

    @Override
    public void perform() {
        ServerCommunication.getInstance().send(id, new GetModelServerAction(view.getModel().serialize()));
    }
}

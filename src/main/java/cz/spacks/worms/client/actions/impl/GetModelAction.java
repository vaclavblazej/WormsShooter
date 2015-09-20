package cz.spacks.worms.client.actions.impl;

import cz.spacks.worms.client.actions.ActionClient;
import cz.spacks.worms.server.ServerCommunication;
import cz.spacks.worms.server.actions.impl.GetModelServerAction;

/**
 * @author Václav Blažej
 */
public class GetModelAction extends ActionClient {

    @Override
    public void perform() {
        ServerCommunication.getInstance().send(id, new GetModelServerAction(view.getModel().serialize()));
    }
}

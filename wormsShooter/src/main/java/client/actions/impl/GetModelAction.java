package client.actions.impl;

import client.actions.ActionClient;
import server.ServerCommunication;
import server.actions.impl.GetModelServerAction;

/**
 * @author Václav Blažej
 */
public class GetModelAction extends ActionClient {

    @Override
    public void perform() {
        ServerCommunication.getInstance().send(id, new GetModelServerAction(view.getModel().serialize()));
    }
}

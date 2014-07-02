package client.actions.impl;

import client.actions.ActionClient;
import server.ServerCommunication;
import server.actions.impl.GetModelServerActionl;

/**
 *
 * @author Skarab
 */
public class GetModelAction extends ActionClient {

    @Override
    public void perform() {
        ServerCommunication.getInstance().send(id, new GetModelServerActionl(view.getModel().serialize()));
    }
}

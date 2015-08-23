package server.actions.impl;

import client.ClientCommunication;
import client.ClientView;
import objects.Body;
import server.actions.ActionServer;
import utilities.communication.Model;
import utilities.communication.SerializableModel;

/**
 * @author Skarab
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
        final Body body = ClientView.getInstance().newBody();
        ClientView.getInstance().setMyBody(body);
        System.out.println("SIZE: " + realModel.getControls().size());
        System.out.println(ClientCommunication.getInstance().getInfo());
    }
}

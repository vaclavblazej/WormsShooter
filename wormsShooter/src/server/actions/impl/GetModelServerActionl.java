package server.actions.impl;

import client.ClientCommunication;
import client.ClientView;
import server.actions.ActionServer;
import utilities.AbstractView;
import utilities.communication.Model;
import utilities.communication.SerializableModel;

/**
 *
 * @author Skarab
 */
public class GetModelServerActionl extends ActionServer {

    public SerializableModel model;

    public GetModelServerActionl(SerializableModel model) {
        this.model = model;
    }

    @Override
    public void perform() {
        System.out.println("Client: model received");
        Model realModel = model.deserialize(view);
        view.setModel(realModel);
        ClientView.getInstance().setMyBody(realModel.getControls().get(ClientCommunication.getInstance().getInfo().getId()));
    }
}

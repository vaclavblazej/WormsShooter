package communication.server;

import client.ClientCommunication;
import client.ClientView;
import utilities.AbstractView;
import utilities.communication.Model;
import utilities.communication.PerformablePacket;
import utilities.communication.SerializableModel;

/**
 *
 * @author Skarab
 */
public class GetModelServerActionl extends PerformablePacket {

    public SerializableModel model;

    public GetModelServerActionl(SerializableModel model) {
        this.model = model;
    }

    @Override
    public void perform(AbstractView view) {
        System.out.println("Client: model received");
        Model realModel = model.deserialize(view);
        view.setModel(realModel);
        ClientCommunication.getInstance().setCounter(ClientView.getInstance().getModel().getCounter());
        ClientView.getInstance().setMyBody(realModel.getControls().get(ClientCommunication.getInstance().getInfo().getId()));
    }
}

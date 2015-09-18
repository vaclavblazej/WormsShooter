import client.ClientCommunication;
import client.actions.impl.*;
import server.ServerCommunication;
import server.actions.impl.*;
import spacks.communication.SCommunication;
import spacks.communication.client.SCommunicationClient;
import spacks.communication.server.SCommunicationServer;
import spacks.communication.utilities.SAction;
import utilities.communication.RegistrationForm;

class ConnectionEstablishing {
    {
        // common
        String address = "localhost";
        int port = 4242;
        // server
        new ServerCommunication(port);
        SCommunicationServer server = SCommunication.createNewServer(port, this);
        server.start();

        final ClientCommunication client = ClientCommunication.getInstance();
        client.init(address, port, new RegistrationForm("username"));
        SCommunicationClient connection = SCommunication.createNewClient(new GetModelAction());
        connection.connect(ip, port);

    }
}

class Documentation {
    {
        // confirm
        client.send(ConfirmAction);

        // connect
        client.send(ConnectAction);
        server.send(SetIdNewPlayerServerAction);
        server.send(GetModelServerAction);
//        server.broadcast(ConnectServerAction);

        // craft
        client.send(CraftAction);
        server.broadcast(CraftServerAction);

        // disconnect
        client.send(DisconnectAction);
        server.send(DisconnectServerAction);

        // get model
        client.send(GetModelAction);
        server.send(GetModelServerAction);

        // mine
        client.send(MineAction);
        server.broadcast(ObtainServerAction);
        server.broadcast(MineServerAction);

        // move
        client.send(MoveAction);
        server.broadcast(MoveServerAction);

        // send chat
        client.send(SendChatAction);
        server.broadcast(SendChatServerAction);

        // shoot
        client.send(ShootAction);
        server.broadcast(ShootServerAction);
    }

    class server {
        static void broadcast(SAction sAction) {
        }

        static void send(SAction sAction) {
        }
    }

    class client {
        static void send(SAction sAction) {
        }
    }
}

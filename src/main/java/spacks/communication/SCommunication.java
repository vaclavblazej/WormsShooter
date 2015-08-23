package spacks.communication;

import spacks.communication.client.SCommunicationClient;
import spacks.communication.client.impl.SCommunicationClientImpl;
import spacks.communication.server.SCommunicationServer;
import spacks.communication.server.impl.SCommunicationServerImpl;

import java.io.IOException;

public class SCommunication {

    public static SCommunicationServer createNewServer(int port) throws IOException {
        return new SCommunicationServerImpl(port);
    }

    public static SCommunicationClient createNewClient() {
        return new SCommunicationClientImpl();
    }
}

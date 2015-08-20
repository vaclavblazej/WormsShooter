package communication;

import communication.backend.client.SCommunicationClient;
import communication.backend.server.SCommunicationServer;
import org.junit.Assert;
import org.junit.Test;
import communication.packets.PrintAction;

public class SCommunicationTest {

    private static final String ip = "localhost";
    private static final int port = 4242;

    public SCommunicationTest() {
    }

    @Test
    public void initialize() throws Exception {
        System.out.println("initialize");
        SCommunicationServer server = new SCommunicationServer(port);
        SCommunicationClient instance = new SCommunicationClient();
        // TODO review the generated test code and remove the default call to fail.
        // fail("The test case is a prototype.");
    }

    @Test
    public void connect() throws Exception {
        System.out.println("connect");
        SCommunicationServer server = new SCommunicationServer(port);
        SCommunicationClient instance = new SCommunicationClient();
        Assert.assertTrue(server.start());
        instance.connect(ip, port);
        instance.stop();
        server.stop();
    }

    @Test
    public void sendPrintAction() throws Exception {
        System.out.println("sendPrintAction");
        SCommunicationServer server = new SCommunicationServer(port);
        SCommunicationClient instance = new SCommunicationClient();
        assert server.start();
        instance.connect(ip, port);
        instance.start();

        for (int i = 0; i < 10; i++) {
            PrintAction clientAction = new PrintAction("Client", String.valueOf(i));
            instance.send(clientAction);
            PrintAction serverAction = new PrintAction("Server", String.valueOf(i));
            server.send(0, serverAction);
        }
        Thread.sleep(50);
        server.stop();
        instance.stop();
    }
}
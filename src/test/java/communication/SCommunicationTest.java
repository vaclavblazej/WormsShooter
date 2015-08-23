package communication;

import communication.backend.client.SCommunicationClient;
import communication.backend.client.impl.SCommunicationClientImpl;
import communication.backend.server.SCommunicationServer;
import communication.backend.utilities.SPacket;
import communication.frontend.utilities.SAction;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import communication.packets.TestAction;

public class SCommunicationTest {

    private static final String ip = "localhost";
    private static final int port = 4242;

    @Before
    public void setup(){
    }

    @Test
    public void serverInitialization() throws Exception {
        SCommunicationServer server = new SCommunicationServer(port);
        server.start();
        Assert.assertTrue(server.isRunning());

        server.stop();
        Assert.assertFalse(server.isRunning());
    }

    @Test
    public void clientInitialization() throws Exception {
        SCommunicationClient client = new SCommunicationClientImpl();
        client.start();

        client.stop();
    }

    @Test
    public void connect() throws Exception {
        SCommunicationServer server = new SCommunicationServer(port);
        SCommunicationClient client = new SCommunicationClientImpl();
        Assert.assertTrue(server.start());
        client.connect(ip, port);
        client.stop();
        server.stop();
    }

    @Test
    public void sendPrintAction() throws Exception {
        SCommunicationServer server = new SCommunicationServer(port);
        server.start();

        SCommunicationClient client = new SCommunicationClientImpl();
        client.connect(ip, port);
        client.start();

        for (int i = 0; i < 10; i++) {
//            TestAction clientAction = new TestAction("Client", String.valueOf(i));
//            client.send(clientAction);
//            TestAction serverAction = new TestAction("Server", String.valueOf(i));
//            server.send(0, serverAction);
        }
        waitForConnection();
        server.stop();
        client.stop();
    }

    @Test
    public void broadcastTest() throws Exception {
        SCommunicationServer server = new SCommunicationServer(port);
        server.start();

        SCommunicationClient client = new SCommunicationClientImpl();
        client.connect(ip, port);
        waitForConnection(); // not necessary for separate client/server

        server.broadcast(new TestAction("Is this delivered?"));
        final TestAction receive = getAction(client.receive().getAction());
        Assert.assertEquals("delivered string is different", "Is this delivered?", receive.getIdent());
    }

    public void waitForConnection() throws InterruptedException {
        Thread.sleep(50);
    }

    public TestAction getAction(SAction action){
        if(action instanceof TestAction){
            return (TestAction) action;
        }else{
            throw new RuntimeException("Bad test action type");
        }
    }
}
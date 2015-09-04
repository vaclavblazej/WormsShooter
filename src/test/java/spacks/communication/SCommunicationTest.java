package spacks.communication;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import spacks.communication.client.SCommunicationClient;
import spacks.communication.packets.TestAction;
import spacks.communication.server.SCommunicationServer;
import spacks.communication.utilities.SAction;

public class SCommunicationTest {

    private static final String ip = "localhost";
    private static final int port = 4242;

    @Before
    public void setup(){
    }

    @Test
    public void serverInitialization() throws Exception {
        SCommunicationServer server = SCommunication.createNewServer(port);
        Assert.assertFalse(server.isRunning());

        server.start();
        Assert.assertTrue(server.isRunning());

        server.stop();
        Assert.assertFalse(server.isRunning());
    }

    @Test
    public void clientInitialization() throws Exception {
        SCommunicationClient client = SCommunication.createNewClient();
        Assert.assertFalse(client.isRunning());

        client.start();
        Assert.assertTrue(client.isRunning());

        client.stop();
        Assert.assertFalse(client.isRunning());
    }

    @Test
    public void connect() throws Exception {
        SCommunicationServer server = SCommunication.createNewServer(port);
        server.start();

        SCommunicationClient client = SCommunication.createNewClient();
        client.connect(ip, port);

        client.stop();
        server.stop();
    }

    @Test
    public void broadcastTest() throws Exception {
        SCommunicationServer server = SCommunication.createNewServer(port);
        server.start();

        SCommunicationClient client = SCommunication.createNewClient();
        client.connect(ip, port);
        waitForConnection(); // not necessary for separate client/server

        server.broadcast(new TestAction("Is this delivered?"));
        final TestAction receive = getAction(client.receive().getAction());
        Assert.assertEquals("Delivered string is different", "Is this delivered?", receive.getIdent());

        server.stop();
        client.stop();
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
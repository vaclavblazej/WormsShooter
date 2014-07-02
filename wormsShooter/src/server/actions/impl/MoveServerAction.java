package server.actions.impl;

import java.awt.Point;
import objects.Body;
import objects.MoveEnum;
import server.actions.ActionServer;
import utilities.AbstractView;

/**
 *
 * @author Skarab
 */
public class MoveServerAction extends ActionServer {

    private int positionX;
    private int positionY;
    private double velocityX;
    private double velocityY;
    private MoveEnum action;

    public MoveServerAction(int id, Point position, Point.Double velocity, MoveEnum action) {
        super(id);
        this.positionX = position.x;
        this.positionY = position.y;
        this.velocityX = velocity.x;
        this.velocityY = velocity.y;
        this.action = action;
    }

    @Override
    public void perform() {
        Body body = view.getModel().getControls().get(id);
        if (body != null) {
            body.setPosition(new Point(positionX, positionY));
//            Exception in thread "Thread-6" java.lang.IndexOutOfBoundsException: Index: 1, Size: 1
//	at java.util.ArrayList.rangeCheck(ArrayList.java:604)
//	at java.util.ArrayList.get(ArrayList.java:382)
//	at utilities.communication.Packet.get(Packet.java:58)
//	at dynamic.communication.move.performClient(move.java:23)
//	at client.ClientCommunication$1.run(ClientCommunication.java:158)
//	at java.lang.Thread.run(Thread.java:724)
            body.setVelocity(new Point.Double(velocityX, velocityY));
            body.control(action);
        }
    }
}

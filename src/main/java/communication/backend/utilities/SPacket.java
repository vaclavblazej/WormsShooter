package communication.backend.utilities;

import communication.frontend.utilities.SAction;
import java.io.Serializable;
import java.util.logging.Logger;

/**
 *
 * @author Štěpán Plachý
 * @author Václav Blažej
 */
public abstract class SPacket implements Serializable {

    private static final transient Logger logger = Logger.getLogger(SPacket.class.getName());

    protected SAction action;
    protected int id;

    public SPacket(SAction action) {
        this.action = action;
        this.id = 0;
    }

    public void performAction() {
        logger.info("Server: action - " + action.toString());
        action.perform();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public void setAction(SAction action) {
        this.action = action;
    }
    
    public SAction getAction() {
        return action;
    }

    public abstract boolean isAsynchronous();
}

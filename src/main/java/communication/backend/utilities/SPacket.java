package communication.backend.utilities;

import communication.frontend.utilities.Performable;
import java.io.Serializable;
import java.util.logging.Logger;

/**
 *
 * @author Štěpán Plachý
 * @author Václav Blažej
 */
public abstract class SPacket implements Serializable {

    private Logger logger = Logger.getLogger(SPacket.class.getName());

    protected Performable action;
    protected int id;

    public SPacket(Performable action) {
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
    
    public void setAction(Performable action) {
        this.action = action;
    }
    
    public Performable getAction() {
        return action;
    }

    public abstract boolean isAsynchronous();
}

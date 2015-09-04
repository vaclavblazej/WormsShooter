package spacks.communication.utilities;

import java.io.Serializable;
import java.util.logging.Logger;

/**
 *
 * @author Stepan Plachy
 * @author Vaclav Blazej
 */
public abstract class SPacket implements Serializable {

    private static final transient Logger logger = Logger.getLogger(SPacket.class.getName());

    protected SAction action;
    protected Integer id;

    public SPacket(SAction action) {
        this(action, 0);
    }

    public SPacket(SAction action, Integer id) {
        this.action = action;
        this.id = id;
    }

    public void performAction() {
        logger.info("Server: action - " + action.toString());
        action.perform();
    }

    public int getId() {
        return id;
    }

    public SAction getAction() {
        return action;
    }

    public abstract boolean isAsynchronous();
}

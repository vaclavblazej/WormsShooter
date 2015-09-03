package spacks.communication.utilities;

/**
 * @author Václav Blažej
 */
public interface SListener {

    void connectionCreated(int id);

    void connectionRemoved(int id);
}

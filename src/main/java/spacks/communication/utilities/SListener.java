package spacks.communication.utilities;

/**
 * @author Vaclav Blazej
 */
public interface SListener {

    void connectionCreated(int id);

    void connectionRemoved(int id);
}

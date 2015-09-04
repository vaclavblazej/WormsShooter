package spacks.communication.utilities;

/**
 *
 * @author Stepan Plachy
 * @author Vaclav Blazej
 */
public class SAsynchronousPacket extends SPacket {

    public SAsynchronousPacket(SAction action) {
        super(action);
    }

    @Override
    public boolean isAsynchronous() {
        return true;
    }
}

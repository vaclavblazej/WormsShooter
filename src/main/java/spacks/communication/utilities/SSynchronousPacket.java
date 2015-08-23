package spacks.communication.utilities;

/**
 *
 * @author Štěpán Plachý
 * @author Václav Blažej
 */
public class SSynchronousPacket extends SPacket {

    private Integer count;

    public SSynchronousPacket(SAction action, Integer count) {
        super(action);
        this.count = count;
    }

    public int getCount() {
        return count;
    }

    public void incrementCount() {
        count++;
    }
    
    public boolean checkSynchronization(int count) {
        return this.count == count + 1;
    }

    @Override
    public boolean isAsynchronous() {
        return false;
    }
}

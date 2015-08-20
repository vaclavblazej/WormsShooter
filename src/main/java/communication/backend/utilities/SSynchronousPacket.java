package communication.backend.utilities;

import communication.frontend.utilities.Performable;

/**
 *
 * @author Štěpán Plachý
 * @author Václav Blažej
 */
public class SSynchronousPacket extends SPacket {

    private int count;

    public SSynchronousPacket(Performable action) {
        super(action);
        this.count = 0;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void incrementCount() {
        count++;
    }
    
    public boolean checkSynchronization(int count) {
        return this.count == count + 1; // functional for overflow
    }

    @Override
    public boolean isAsynchronous() {
        return false;
    }
}

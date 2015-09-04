package spacks.communication.utilities;

import java.io.Serializable;

/**
 * Interface of action capable of performing itself on remote host.
 *
 * @author Stepan Plachy
 * @author Vaclav Blazej
 * @see spacks.communication.utilities.SAsynchronousPacket
 * @see spacks.communication.utilities.SSynchronousPacket
 * @since SAPI Communication 1.0
 */
public interface SAction extends Serializable {

    void perform();
}

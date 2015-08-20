package communication.frontend.utilities;

import java.io.Serializable;

/**
 * Interface of action capable of performing itself on remote host.
 *
 * @author Štěpán Plachý
 * @author Václav Blažej
 * @see communication.backend.utilities.SAsynchronousPacket
 * @see communication.backend.utilities.SSynchronousPacket
 * @since SAPI Communication 1.0
 */
public interface Performable extends Serializable {

    void perform();
}

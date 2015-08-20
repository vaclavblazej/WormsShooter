package communication.backend.utilities;

import java.io.Serializable;

/**
 *
 * @author Štěpán Plachý
 * @author Václav Blažej
 */
public class SCommunicationInformation implements Serializable {

    protected int id;

    public SCommunicationInformation(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}

package utilities;

import java.io.Serializable;

/**
 *
 * @author plach_000
 */
public class RegistrationForm implements Serializable {

    private int socket;

    public RegistrationForm(int socket) {
        this.socket = socket;
    }

    public int getSocket() {
        return socket;
    }
}

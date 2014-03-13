package utilities.communication;

import java.io.Serializable;

/**
 *
 * @author plach_000
 */
public class RegistrationForm implements Serializable {

    private String name;

    public RegistrationForm(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

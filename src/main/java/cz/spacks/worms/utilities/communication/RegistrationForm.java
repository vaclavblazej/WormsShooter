package cz.spacks.worms.utilities.communication;

import java.io.Serializable;

/**
 * @author Štìpán Plachý
 * @author Václav Blažej
 */
public class RegistrationForm implements Serializable{

    private String name;

    public RegistrationForm(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

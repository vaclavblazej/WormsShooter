package cz.spacks.worms.model.communication;

import java.io.Serializable;

/**
 *
 *
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

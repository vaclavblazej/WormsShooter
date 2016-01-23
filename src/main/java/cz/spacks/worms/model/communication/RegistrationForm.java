package cz.spacks.worms.model.communication;

import java.io.Serializable;

/**
 *
 *
 */
public class RegistrationForm implements Serializable {

    private String name;
    private Integer id;

    public RegistrationForm(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}

package cz.spacks.worms.utilities.communication;

import java.io.Serializable;

/**
 * @author �t�p�n Plach�
 * @author V�clav Bla�ej
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

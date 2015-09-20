package cz.spacks.worms.utilities;

import cz.spacks.worms.utilities.communication.RegistrationForm;

/**
 * Information about player.
 *
 * @author �t�p�n Plach�
 * @author V�clav Bla�ej
 */
public class PlayerInfo {

    private final String name;
    private final int id;

    public PlayerInfo(int id) {
        this.id = id;
        name = "";
    }

    public PlayerInfo(int id, RegistrationForm form) {
        this.id = id;
        this.name = form.getName();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}

package cz.spacks.worms.controller;

import cz.spacks.worms.model.communication.RegistrationForm;

/**
 * Information about player.
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

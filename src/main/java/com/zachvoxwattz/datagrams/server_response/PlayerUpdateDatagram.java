package com.zachvoxwattz.datagrams.server_response;

import com.zachvoxwattz.models.entities.Player;

/**
 * Message datagram for the {@code sv-res-all-player-update} response.
 */
public class PlayerUpdateDatagram {
    /**
     * Player's unique ID
     */
    private String id;

    /**
     * Player's custom in-game name.
     */
    private String name;

    /**
     * Player's selected avatar ID.
     */
    private String avatarID;

    /**
     * Player's selected avatar background color hex code.
     */
    private String avatarBackgroundHex;

    /**
     * Player's selected avatar background color alpha.
     */
    private float avatarBackgroundAlpha;

    /**
     * Player's selected dice set body color hex code.
     */
    private String diceBodyHex;

    /**
     * Player's selected dice set body color alpha.
     */
    private float diceBodyAlpha;

    /**
     * Player's selected dice set face color hex code.
     */
    private String diceFaceHex;

    /**
     * Player's selected dice set face color alpha.
     */
    private float diceFaceAlpha;

    public PlayerUpdateDatagram() {}

    public PlayerUpdateDatagram(Player pl) {
        this.id = pl.getID();
        this.name = pl.getName();
        this.avatarID = pl.getAvatarID();
        this.avatarBackgroundHex = pl.getAvatarBackgroundHex();
        this.avatarBackgroundAlpha = pl.getAvatarBackgroundAlpha();
        this.diceBodyHex = pl.getDiceBodyHex();
        this.diceBodyAlpha = pl.getDiceBodyAlpha();
        this.diceFaceHex = pl.getDiceFaceHex();
        this.diceFaceAlpha = pl.getDiceFaceAlpha();
    }

    /*
        Getters and setters.
     */
    public String getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getAvatarID() {
        return this.avatarID;
    }

    public String getAvatarBackgroundHex() {
        return this.avatarBackgroundHex;
    }

    public float getAvatarBackgroundAlpha() {
        return this.avatarBackgroundAlpha;
    }

    public String getDiceBodyHex() {
        return this.diceBodyHex;
    }

    public float getDiceBodyAlpha() {
        return this.diceBodyAlpha;
    }

    public String getDiceFaceHex() {
        return this.diceFaceHex;
    }

    public float getDiceFaceAlpha() {
        return this.diceFaceAlpha;
    }
}

package com.zachvoxwattz.models.entities;

import com.zachvoxwattz.datagrams.client_request.RegistrationRequestDatagram;

/**
 * Representing each individual connected players in the server.
 * 
 * <p>Consists of many properties belonging to themselves.
 */
public class Player {
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

    public Player() {}
    public Player(String name,String avatarID,String avatarBackgroundHex,float avatarBackgroundAlpha,String diceBodyHex,float diceBodyAlpha,String diceFaceHex,float diceFaceAlpha) {
        this.name = name;
        this.avatarID = avatarID;
        this.avatarBackgroundHex = avatarBackgroundHex;
        this.avatarBackgroundAlpha = avatarBackgroundAlpha;
        this.diceBodyHex = diceBodyHex;
        this.diceBodyAlpha = diceBodyAlpha;
        this.diceFaceHex = diceFaceHex;
        this.diceFaceAlpha = diceFaceAlpha;
    }

    public Player(RegistrationRequestDatagram datagram, String clientID) {
        this.name = datagram.getName();
        this.avatarID = datagram.getAvatarID();
        this.avatarBackgroundHex = datagram.getAvatarBackgroundHex();
        this.avatarBackgroundAlpha = datagram.getAvatarBackgroundAlpha();
        this.diceBodyHex = datagram.getDiceBodyHex();
        this.diceBodyAlpha = datagram.getDiceBodyAlpha();
        this.diceFaceHex = datagram.getDiceFaceHex();
        this.diceFaceAlpha = datagram.getDiceFaceAlpha();
    }

    @Override
    public String toString() {
        var returnedString = String.format(
            "Player ID '%s' has following properties:\n\t- Name: %s\n\t- ID of avatar: %s\n\t- Ava. Background Hex: %s\n\t- Ava. Background Alpha: %.2f\n\t- Dice Background Hex: %s\n\t- Dice Background Alpha: %.2f\n\t- Dice Face Hex: %s\n\t- Dice Face Alpha: %.2f",
            this.name,
            this.avatarID,
            this.avatarBackgroundHex,
            this.avatarBackgroundAlpha,
            this.diceBodyHex,
            this.diceBodyAlpha,
            this.diceFaceHex,
            this.diceFaceAlpha
        );

        return returnedString;
    }

    /*
        Getters and setters.
     */
    public String getName() {
        return this.name;
    }

    public void setName(String value) {
        this.name = value;
    }

    public String getAvatarID() {
        return this.avatarID;
    }

    public void setAvatarID(String value) {
        this.avatarID = value;
    }

    public String getAvatarBackgroundHex() {
        return this.avatarBackgroundHex;
    }

    public void setAvatarBackgroundHex(String value) {
        this.avatarBackgroundHex = value;
    }

    public float getAvatarBackgroundAlpha() {
        return this.avatarBackgroundAlpha;
    }

    public void setAvatarBackgroundAlpha(float value) {
        this.avatarBackgroundAlpha = value;
    }

    public String getDiceBodyHex() {
        return this.diceBodyHex;
    }

    public void setDiceBodyHex(String value) {
        this.diceBodyHex = value;
    }

    public float getDiceBodyAlpha() {
        return this.diceBodyAlpha;
    }

    public void setDiceBodyAlpha(float value) {
        this.diceBodyAlpha = value;
    }

    public String getDiceFaceHex() {
        return this.diceFaceHex;
    }

    public void setDiceFaceHex(String value) {
        this.diceFaceHex = value;
    }

    public float getDiceFaceAlpha() {
        return this.diceFaceAlpha;
    }

    public void setDiceFaceAlpha(float value) {
        this.diceFaceAlpha = value;
    }
}

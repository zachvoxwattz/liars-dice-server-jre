package com.zachvoxwattz.datagrams;

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
    public PlayerUpdateDatagram(String id, String name,String avatarID,String avatarBackgroundHex,float avatarBackgroundAlpha,String diceBodyHex,float diceBodyAlpha,String diceFaceHex,float diceFaceAlpha) {
        this.id = id;
        this.name = name;
        this.avatarID = avatarID;
        this.avatarBackgroundHex = avatarBackgroundHex;
        this.avatarBackgroundAlpha = avatarBackgroundAlpha;
        this.diceBodyHex = diceBodyHex;
        this.diceBodyAlpha = diceBodyAlpha;
        this.diceFaceHex = diceFaceHex;
        this.diceFaceAlpha = diceFaceAlpha;
    }

    /*
        Getters and setters.
     */
    public String getId() {
        return this.id;
    }

    public void setId(String value) {
        this.id = value;
    }

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

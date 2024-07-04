package com.zachvoxwattz.datagrams.client_request;

/**
 * Message datagram for the {@code cl-req-register} request.
 */
public class RegistrationRequestDatagram {
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

    public RegistrationRequestDatagram() {}
    public RegistrationRequestDatagram(String a, String b, String c, float d, String e, float f, String g, float h) {
        this.name = a;
        this.avatarID = b;
        this.avatarBackgroundHex = c;
        this.avatarBackgroundAlpha = d;
        this.diceBodyHex = e;
        this.diceBodyAlpha = f;
        this.diceFaceHex = g;
        this.diceFaceAlpha = h;
    }

    /*
        Getters and setters.
     */     
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

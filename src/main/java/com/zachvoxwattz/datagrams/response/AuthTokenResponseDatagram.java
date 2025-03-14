package com.zachvoxwattz.datagrams.response;

/**
 * Message datagram for the {@code sv-res-authtoken} response.
 */
public class AuthTokenResponseDatagram {
    private String authToken;

    public AuthTokenResponseDatagram() {}
    public AuthTokenResponseDatagram(String key) {
        this.authToken = key;
    }

    /*
        Getters and setters.
     */
    public String getAuthToken() {
        return this.authToken;
    }

    public void setAuthToken(String value) {
        this.authToken = value;
    }
}

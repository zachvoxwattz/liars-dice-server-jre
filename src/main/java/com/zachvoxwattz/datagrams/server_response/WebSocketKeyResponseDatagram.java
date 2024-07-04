package com.zachvoxwattz.datagrams.server_response;

/**
 * Message datagram for the {@code sv-res-wesockey} response.
 */
public class WebSocketKeyResponseDatagram {
    private String wsKey;

    public WebSocketKeyResponseDatagram() {}
    public WebSocketKeyResponseDatagram(String key) {
        this.wsKey = key;
    }

    /*
        Getters and setters.
     */
    public String getWsKey() {
        return this.wsKey;
    }

    public void setWsKey(String value) {
        this.wsKey = value;
    }
}

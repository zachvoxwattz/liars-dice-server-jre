package com.zachvoxwattz.datagrams.response;

/**
 * Message datagram for the {@code sv-res-register} response.
 */
public class RegistrationResponseDatagram {
    /**
     * For the win!
     */
    private boolean received;

    public RegistrationResponseDatagram() {}
    public RegistrationResponseDatagram(boolean req) {
        this.received = req;
    }

    /*
        Getters.
     */
    public boolean getReceivedStatus() {
        return this.received;
    }
}

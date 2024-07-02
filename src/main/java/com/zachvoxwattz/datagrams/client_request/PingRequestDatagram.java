package com.zachvoxwattz.datagrams.client_request;

/**
 * Message datagram for the {@code cl-req-ping} request.
 */
public class PingRequestDatagram {
    /**
     * For the win!
     */
    private boolean pingRequest;

    public PingRequestDatagram() {}
    public PingRequestDatagram(boolean req) {
        this.pingRequest = req;
    }

    /*
        Getters.
     */
    public boolean getPingRequest() {
        return this.pingRequest;
    }
}

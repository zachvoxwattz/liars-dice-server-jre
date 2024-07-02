package com.zachvoxwattz.datagrams.server_response;

/**
 * Message datagram for the {@code sv-res-ping} response.
 */
public class PingResponseDatagram {
    /**
     * For the win!
     */
    private int no;

    public PingResponseDatagram() {}
    public PingResponseDatagram(int ftw) {
        this.no = ftw;
    }

    /*
        Getters.
     */
    public int getNo() {
        return this.no;
    }
}

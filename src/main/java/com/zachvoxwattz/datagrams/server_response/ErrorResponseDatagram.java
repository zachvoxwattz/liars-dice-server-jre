package com.zachvoxwattz.datagrams.server_response;

/**
 * Message datagram for the {@code sv-res-error} response.
 */
public class ErrorResponseDatagram {
    private int errorCode;
    private String errorMessage;

    public ErrorResponseDatagram() {}
    public ErrorResponseDatagram(int errcode, String errmsg) {
        this.errorCode = errcode;
        this.errorMessage = errmsg;
    }

    /*
        Getters.
     */
    public int getErrorCode() {
        return this.errorCode;
    }

    public String getErrorMessage() {
        return this.errorMessage;
    }
}

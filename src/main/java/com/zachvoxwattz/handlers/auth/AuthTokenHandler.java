package com.zachvoxwattz.handlers.auth;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;

import com.zachvoxwattz.core.MainServer;
import com.zachvoxwattz.core.event_string.entity.ResVar;
import com.zachvoxwattz.datagrams.response.AuthTokenResponseDatagram;

import com.zachvoxwattz.handlers.AbstractHandler;

import com.zachvoxwattz.utils.AuthTokenGenerator;

/**
 * Handler for responding a client with an auth token.
 */
public class AuthTokenHandler extends AbstractHandler<Void> {
    public AuthTokenHandler(MainServer mainServer) {
        super(mainServer);
    }

    @Override
    public void onEventExecution(SocketIOClient client, Void data, AckRequest ackSender) {
        var authToken = AuthTokenGenerator.generateToken();
        AuthTokenResponseDatagram datagram = new AuthTokenResponseDatagram(authToken);
        
        client.sendEvent(
            this.eventStringProvider.getSVEvent(ResVar.AUTH_TOKEN),
            datagram
        );
    }
}

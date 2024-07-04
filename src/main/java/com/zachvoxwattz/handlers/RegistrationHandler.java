package com.zachvoxwattz.handlers;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import com.zachvoxwattz.core.GameServer;
import com.zachvoxwattz.datagrams.client_request.RegistrationRequestDatagram;
import com.zachvoxwattz.datagrams.server_response.RegistrationResponseDatagram;

/**
 * Implemented {@code AbstractHandler} class.
 * 
 * <p>Responsible for registering player data coming from requests from clients.
 */
public class RegistrationHandler extends AbstractHandler<RegistrationRequestDatagram> {
    /**
     * Request event name.
     */
    public static String REQ_EVENT_NAME = "cl-req-register";

    /**
     * Response event name.
     */
    public static String RES_EVENT_NAME = "sv-res-register";
    
    public RegistrationHandler(GameServer mainServer) {
        super(mainServer);
    }

    @Override
    public void onData(SocketIOClient client, RegistrationRequestDatagram data, AckRequest ackSender) throws Exception {
        var userManager = this.getMainServer().getUserManager();
        var clientID = client.getSessionId().toString();
        userManager.addPlayer(data, clientID);

        this.getMainServer().debugPrintf("Registered player ID '{}' into the lobby", clientID);
        client.sendEvent(RES_EVENT_NAME, new RegistrationResponseDatagram(true));
    }
    
}

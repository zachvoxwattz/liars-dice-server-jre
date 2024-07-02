package com.zachvoxwattz.handlers;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import com.zachvoxwattz.core.GameServer;
import com.zachvoxwattz.datagrams.PlayerInfoDatagram;

/**
 * Implemented {@code AbstractHandler} class.
 * 
 * <p>Responsible for registering player data coming from requests from clients.
 */
public class PlayerDataReceiver extends AbstractHandler<PlayerInfoDatagram> {
    /**
     * Request event name.
     */
    public static String REQ_EVENT_NAME = "cl-req-register-data";

    /**
     * Response event name.
     */
    public static String RES_EVENT_NAME = "sv-res-register-data-ack";
    
    public PlayerDataReceiver(GameServer mainServer) {
        super(mainServer);
    }

    @Override
    public void onData(SocketIOClient client, PlayerInfoDatagram data, AckRequest ackSender) throws Exception {

    }
    
}

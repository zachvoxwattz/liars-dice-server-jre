package com.zachvoxwattz.handlers;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import com.zachvoxwattz.core.GameServer;
import com.zachvoxwattz.datagrams.client_request.PingRequestDatagram;
import com.zachvoxwattz.datagrams.server_response.PingResponseDatagram;

/**
 * Implemented {@code AbstractHandler} class.
 * 
 * <p>Responsible for responding to incoming ping requests from clients.
 */
public class PlayerPingHandler extends AbstractHandler<PingRequestDatagram> {
    /**
     * Request event name.
     */
    public static String REQ_EVENT_NAME = "cl-req-ping";

    /**
     * Response event name.
     */
    public static String RES_EVENT_NAME = "sv-res-ping";
    
    public PlayerPingHandler(GameServer parentComponent) {
        super(parentComponent);
    }

    @Override
    public void onData(SocketIOClient client, PingRequestDatagram data, AckRequest ackSender) throws Exception {
        var toBeSentData = new PingResponseDatagram(69420);
        client.sendEvent(RES_EVENT_NAME, toBeSentData);

        this.getMainServer().debugPrintf(
            "Client '{}' invoked ping request. Responded with {}.",
            client.getSessionId(), 
            toBeSentData.getNo()
        );
    }
    
}

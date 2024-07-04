package com.zachvoxwattz.handlers;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import com.zachvoxwattz.core.GameServer;

/**
 * Implemented {@code AbstractHandler} class.
 * 
 * <p>Responsible for responding to incoming ping requests from clients.
 */
public class PingHandler extends AbstractHandler<Void> {
    /**
     * Request event name.
     */
    public static String REQ_EVENT_NAME = "cl-req-ping";

    /**
     * Response event name.
     */
    public static String RES_EVENT_NAME = "sv-res-ping";
    
    public PingHandler(GameServer parentComponent) {
        super(parentComponent);
    }

    @Override
    public void onData(SocketIOClient client, Void data, AckRequest ackSender) throws Exception {
        client.sendEvent(RES_EVENT_NAME);
        this.getMainServer().debugPrintf("Client '{}' invoked ping request. Responded to request.", client.getSessionId());
    }
    
}

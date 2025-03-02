package com.zachvoxwattz.handlers.ping;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;

import com.zachvoxwattz.core.GameServer;

import com.zachvoxwattz.handlers.AbstractHandler;

import com.zachvoxwattz.shared.ResponseEventType;

/**
 * Implemented {@code AbstractHandler} class.
 * 
 * <p>Responsible for responding to incoming ping requests from clients.
 */
public class PingHandler extends AbstractHandler<Void> {
    public PingHandler(GameServer parentComponent) {
        super(parentComponent);
    }

    @Override
    public void onData(SocketIOClient client, Void data, AckRequest ackSender) throws Exception {
        client.sendEvent(ResponseEventType.PING);
        this.getMainServer().debugPrintf("Client '%s' invoked ping request. Responded to request.", client.getSessionId());
    }
    
}

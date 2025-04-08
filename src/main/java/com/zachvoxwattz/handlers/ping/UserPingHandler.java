package com.zachvoxwattz.handlers.ping;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;

import com.zachvoxwattz.core.MainServer;
import com.zachvoxwattz.core.logging.LogService;

import com.zachvoxwattz.handlers.AbstractHandler;

/**
 * Implemented {@code AbstractHandler} class.
 * 
 * <p>Responsible for responding to incoming ping requests from clients.
 */
public class UserPingHandler extends AbstractHandler<Void> {
    public UserPingHandler(MainServer parentComponent, boolean requiresAuth) {
        super(parentComponent, requiresAuth);
    }

    @Override
    public void onEventExecution(SocketIOClient client, Void data, AckRequest ackSender) {
        client.sendEvent(this.eventStringProvider.getEventString("sv-ping"));
        LogService.logDebug("Client '%s' invoked ping request. Responded to request.", client.getSessionId());
    }
}

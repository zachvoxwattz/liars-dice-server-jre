package com.zachvoxwattz.core.handlers.connectivity;

import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.listener.ConnectListener;

import com.zachvoxwattz.core.ClientManager;
import com.zachvoxwattz.core.logging.LogSentry;

import com.zachvoxwattz.datagrams.response.ErrorResponseDatagram;

public class ConnectHandler implements ConnectListener {
    /**
     * Main server instance.
     */
    private ClientManager clientManager;

    public ConnectHandler(ClientManager parent) {
        this.clientManager = parent;
    }

    @Override
    public void onConnect(SocketIOClient client) {
        // Retrieves client ID.
        var clientID = client.getSessionId();
        LogSentry.logInfo("Client ID '%s' connected.", clientID);

        // If the server no longer accepts connections, deny new ones.
        if (!this.clientManager.acceptConnections()) {
            var errorDatagram = new ErrorResponseDatagram(503, "Server no longer accepts new connection!");
            
            client.sendEvent(
                this.clientManager.getEventStringProvider().getEventString("sv-error"),
                errorDatagram
            );
            client.disconnect();

            LogSentry.logDebug("Refusing client ID '%s' as server no longer accepts new connection.", clientID);
            return;
        }
    }
}

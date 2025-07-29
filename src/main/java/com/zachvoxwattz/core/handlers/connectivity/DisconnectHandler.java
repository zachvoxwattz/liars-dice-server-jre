package com.zachvoxwattz.core.handlers.connectivity;

import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.listener.DisconnectListener;

import com.zachvoxwattz.core.ClientManager;
import com.zachvoxwattz.core.logging.LogSentry;

public class DisconnectHandler implements DisconnectListener {
    /**
     * Main server instance.
     */
    private ClientManager clientManager;

    public DisconnectHandler(ClientManager parent) {
        this.clientManager = parent;
    }

    @Override
    public void onDisconnect(SocketIOClient client) {
        var clientID = client.getSessionId();
        LogSentry.logInfo("Client ID '%s' has disconnected.", clientID);
    }

    /**
     * @return {@code ClientManager} object
     */
    public ClientManager getClientManager() {
        return this.clientManager;
    }
}
package com.zachvoxwattz.handlers.connection;

import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.listener.DisconnectListener;
import com.zachvoxwattz.core.MainServer;
import com.zachvoxwattz.core.logging.LogService;

/**
 * Implemented disconnect handler for added features.
 */
public class DisconnectHandler implements DisconnectListener {
    /**
     * Main server instance.
     */
    private MainServer mainServer;

    public DisconnectHandler(MainServer parent) {
        this.mainServer = parent;
    }

    @Override
    public void onDisconnect(SocketIOClient client) {
        var clientID = client.getSessionId();
        LogService.logInfo("Client ID '%s' has disconnected.", clientID);

        // Decreases the number of connections.
        var numberOfConnectionsAfter = this.mainServer.getClientCount() - 1;
        if (numberOfConnectionsAfter < MainServer.MAX_CONNECTED_CLIENTS) this.mainServer.acceptConnections(true);
    }
}

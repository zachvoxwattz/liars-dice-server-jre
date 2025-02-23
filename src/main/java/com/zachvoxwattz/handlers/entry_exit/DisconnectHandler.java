package com.zachvoxwattz.handlers.entry_exit;

import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.listener.DisconnectListener;
import com.zachvoxwattz.core.GameServer;
import com.zachvoxwattz.core.LogService;

/**
 * Implemented disconnect handler for added features.
 */
public class DisconnectHandler implements DisconnectListener {
    /**
     * Main GameServer.
     */
    private GameServer mainServer;

    public DisconnectHandler(GameServer parent) {
        this.mainServer = parent;
    }

    @Override
    public void onDisconnect(SocketIOClient client) {
        var clientID = client.getSessionId();
        LogService.logInfo("Client ID '%s' has disconnected.", clientID);

        // Decreases the number of connections.
        var numberOfConnections = this.mainServer.getSocketIOInstance().getAllClients().size();
        if (numberOfConnections < GameServer.MAX_CONNECTED_CLIENTS) this.mainServer.acceptConnections(true);
    }
}

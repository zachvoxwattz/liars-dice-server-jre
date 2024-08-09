package com.zachvoxwattz.handlers.entry_exit;

import org.apache.logging.log4j.Logger;

import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.listener.DisconnectListener;
import com.zachvoxwattz.core.GameServer;

/**
 * Implemented disconnect handler for added features.
 */
public class DisconnectHandler implements DisconnectListener {
    /**
     * Main GameServer.
     */
    private GameServer mainServer;

    /**
     * Main GameServer logger.
     */
    private static Logger gsLogger;

    public DisconnectHandler(GameServer parent) {
        this.mainServer = parent;
        gsLogger = this.mainServer.getLogger();
    }

    @Override
    public void onDisconnect(SocketIOClient client) {
        var clientID = client.getSessionId();
        gsLogger.info("Client ID '{}' has disconnected.", clientID);

        // Decreases the number of connections.
        var numberOfConnections = this.mainServer.getSocketIOInstance().getAllClients().size();
        if (numberOfConnections < GameServer.MAX_CONNECTED_CLIENTS) this.mainServer.acceptConnections(true);
    }
}

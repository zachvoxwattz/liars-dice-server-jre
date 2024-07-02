package com.zachvoxwattz.handlers.entry_exit;

import org.apache.logging.log4j.Logger;

import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.zachvoxwattz.core.GameServer;

/**
 * Implemented connection handler for added features.
 */
public class ConnectHandler implements ConnectListener {
    /**
     * Main GameServer.
     */
    private GameServer mainServer;

    /**
     * Main GameServer logger.
     */
    private static Logger gsLogger;

    public ConnectHandler(GameServer parent) {
        this.mainServer = parent;
        gsLogger = this.mainServer.getLogger();
    }

    @Override
    public void onConnect(SocketIOClient cl) {
        var clientID = cl.getSessionId();
        var clientIP = cl.getHandshakeData().getAddress().getHostString();
        var clientPort = cl.getHandshakeData().getAddress().getPort();
        gsLogger.info("Client ID '{}' connected via {}:{}", clientID, clientIP, clientPort);

        // If there is at least one connected player, creates a lobby.
        var numberOfConnections = this.mainServer.getSocketIOInstance().getAllClients().size();
        if (numberOfConnections > 0 && !this.mainServer.hasLobby()) {
            this.mainServer.createLobby();
            this.mainServer.debugPrintf("Creating a lobby as there is at least one connected player.");
        }
    }
}

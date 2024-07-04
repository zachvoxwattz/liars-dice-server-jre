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
     * Connection refusal event name.
     */
    private static String CONNECTION_DENIED_EVENT_NAME = "sv-res-deny-connection";

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
    public void onConnect(SocketIOClient client) {
        // Retrieves client ID.
        var clientID = client.getSessionId();

        // If the server no longer accepts connections, deny new ones.
        if (!this.mainServer.acceptConnections()) {
            client.sendEvent(CONNECTION_DENIED_EVENT_NAME);
            client.disconnect();
            this.mainServer.debugPrintf("Refusing client ID '{}' as server no longer accepts new connection.", clientID);
            return;
        }

        else {
            var clientIP = client.getHandshakeData().getAddress().getHostString();
            var clientPort = client.getHandshakeData().getAddress().getPort();
            gsLogger.info("Client ID '{}' connected via {}:{}", clientID, clientIP, clientPort);

            // Checks for the number players to prevent further connections.
            var numberOfConnections = this.mainServer.getSocketIOInstance().getAllClients().size();
            if (numberOfConnections + 1 > GameServer.MAX_CONNECTED_CLIENTS) this.mainServer.acceptConnections(false);
        }

        // If this connection is the first one to connect to the server, creates a lobby.
        if (!this.mainServer.hasLobby()) {
            this.mainServer.createLobby();
            this.mainServer.debugPrintf("Creating a lobby as there is at least one connected player.");
        }
    }
}

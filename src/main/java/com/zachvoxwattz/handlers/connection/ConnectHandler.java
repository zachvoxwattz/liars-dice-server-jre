package com.zachvoxwattz.handlers.connection;

import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.zachvoxwattz.core.GameServer;
import com.zachvoxwattz.core.LogService;
import com.zachvoxwattz.datagrams.server_response.ErrorResponseDatagram;

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

    public ConnectHandler(GameServer parent) {
        this.mainServer = parent;
    }

    @Override
    public void onConnect(SocketIOClient client) {
        // Retrieves client ID.
        var clientID = client.getSessionId();

        // If the server no longer accepts connections, deny new ones.
        if (!this.mainServer.acceptConnections()) {
            var errorDatagram = new ErrorResponseDatagram(503, "Server no longer accepts new connection!");
            client.sendEvent(CONNECTION_DENIED_EVENT_NAME, errorDatagram);
            client.disconnect();

            this.mainServer.debugPrintf("Refusing client ID '%s' as server no longer accepts new connection.", clientID);
            return;
        }

        else {
            var clientIP = client.getHandshakeData().getAddress().getHostString();
            var clientPort = client.getHandshakeData().getAddress().getPort();
            LogService.logInfo("Client ID '%s' connected via %s:%s", clientID, clientIP, clientPort);

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

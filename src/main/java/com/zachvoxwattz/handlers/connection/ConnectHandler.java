package com.zachvoxwattz.handlers.connection;

import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.zachvoxwattz.core.MainServer;
import com.zachvoxwattz.core.event_string.entity.ResVar;
import com.zachvoxwattz.core.logging.LogService;
import com.zachvoxwattz.datagrams.response.ErrorResponseDatagram;

/**
 * Implemented connection handler for added features.
 */
public class ConnectHandler implements ConnectListener {
    /**
     * Main server instance.
     */
    private MainServer mainServer;

    public ConnectHandler(MainServer parent) {
        this.mainServer = parent;
    }

    @Override
    public void onConnect(SocketIOClient client) {
        // Retrieves client ID.
        var clientID = client.getSessionId();

        // If the server no longer accepts connections, deny new ones.
        if (!this.mainServer.acceptConnections()) {
            var errorDatagram = new ErrorResponseDatagram(503, "Server no longer accepts new connection!");
            
            client.sendEvent(
                this.mainServer.getEventStringProvider().getSVEvent(ResVar.ERR_NO_CONNECT),
                errorDatagram
            );
            client.disconnect();

            LogService.logDebug("Refusing client ID '%s' as server no longer accepts new connection.", clientID);
            return;
        }

        else {
            var clientIP = client.getHandshakeData().getAddress().getHostString();
            var clientPort = client.getHandshakeData().getAddress().getPort();
            LogService.logInfo("Client ID '%s' connected via %s:%s", clientID, clientIP, clientPort);

            // Checks for the number players to prevent further connections.
            var numberOfConnections = this.mainServer.getClientCount();
            if (numberOfConnections + 1 > MainServer.MAX_CONNECTED_CLIENTS) this.mainServer.acceptConnections(false);
        }

        // If this connection is the first one to connect to the server, creates a lobby.
        if (!this.mainServer.hasLobby()) {
            this.mainServer.createLobby();
            LogService.logDebug("Creating a lobby as there is at least one connected player.");
        }
    }
}

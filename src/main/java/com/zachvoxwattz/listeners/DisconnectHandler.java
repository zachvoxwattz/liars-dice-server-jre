package com.zachvoxwattz.listeners;

import org.apache.logging.log4j.Logger;

import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.listener.DisconnectListener;
import com.zachvoxwattz.core.GameServer;

public class DisconnectHandler implements DisconnectListener {
    /**
     * Main GameServer.
     */
    private GameServer parentComponent;

    /**
     * Main GameServer logger.
     */
    private static Logger gsLogger;

    public DisconnectHandler(GameServer parent) {
        this.parentComponent = parent;
        gsLogger = this.parentComponent.getLogger();
    }

    @Override
    public void onDisconnect(SocketIOClient cl) {
        var clientID = cl.getSessionId();
        var clientIP = cl.getHandshakeData().getAddress().getHostString();
        var clientPort = cl.getHandshakeData().getAddress().getPort();
        gsLogger.info("Client ID '{}' has disconnected from {}:{}", clientID, clientIP, clientPort);
    }
}

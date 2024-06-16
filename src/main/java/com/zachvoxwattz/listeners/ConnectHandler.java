package com.zachvoxwattz.listeners;

import org.apache.logging.log4j.Logger;

import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.zachvoxwattz.core.GameServer;

public class ConnectHandler implements ConnectListener {
    /**
     * Main GameServer.
     */
    private GameServer parentComponent;

    /**
     * Main GameServer logger.
     */
    private static Logger gsLogger;

    public ConnectHandler(GameServer parent) {
        this.parentComponent = parent;
        gsLogger = this.parentComponent.getLogger();
    }

    @Override
    public void onConnect(SocketIOClient cl) {
        var clientIP = cl.getHandshakeData().getAddress().getAddress().getHostAddress();
        var clientPort = cl.getHandshakeData().getAddress().getPort();
        gsLogger.info("Client {} has connected via port {}!", clientIP, clientPort);
    }
}

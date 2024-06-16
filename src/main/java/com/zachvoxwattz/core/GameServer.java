package com.zachvoxwattz.core;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOServer;
import com.zachvoxwattz.listeners.ConnectHandler;
import com.zachvoxwattz.listeners.DisconnectHandler;

/**
 * The main game server.
 */
public class GameServer extends SocketIOServer {
    /**
     * Server logger.
     */
    private static Logger gsLogger = LogManager.getLogger("GameServer");
    
    /**
     * Server debug mode value.
     */
    private boolean debugMode;

    /**
     * Constructor initializing the Socket.IO server.
     * @param configuration Object containing some crucial
     * properties for the server to boot and run.
     */
    public GameServer(Configuration serverConfig, boolean debugMode) {
        super(serverConfig);
        this.debugMode = debugMode;
        this.attachListeners();
    }

    /**
     * Binds various event listeners to the server.
     */
    private void attachListeners() {
        this.addConnectListener(new ConnectHandler(this));
        this.addDisconnectListener(new DisconnectHandler(this));
    }

    /**
     * Terminates and stops execution of the game server.
     * <p>
     * All I/Os should be handled with care before shutting down!
     */
    public void terminate() {

    }

    /**
     * Logger object of the GameServer.
     * @return {@code Logger} object.
     */
    public Logger getLogger() {
        return gsLogger;
    }

    /**
     * Debug mode of the GameServer.
     */
    public boolean getDebugMode() {
        return this.debugMode;
    }
}

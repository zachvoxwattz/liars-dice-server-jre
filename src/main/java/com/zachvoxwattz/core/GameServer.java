package com.zachvoxwattz.core;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.Transport;
import com.zachvoxwattz.listeners.ConnectHandler;
import com.zachvoxwattz.listeners.DisconnectHandler;
import com.zachvoxwattz.listeners.PlayerPingHandler;

/**
 * The main game server.
 */
public class GameServer {
    /**
     * Server logger.
     */
    private static Logger gsLogger = LogManager.getLogger("GameServer");
    
    /**
     * Server debug mode value.
     */
    private boolean debugMode;

    /**
     * Socket.IO instance for the server.
     */
    private SocketIOServer socketIOInstance;

    /**
     * Constructor initializing the Socket.IO server.
     * @param configuration Object containing some crucial
     * properties for the server to boot and run.
     */
    public GameServer(int port, boolean debugMode) {
        this.debugMode = debugMode;

        // Constructs a Configuration object for starting the server.
        var config = new Configuration();
        config.setHostname("0.0.0.0");
        config.setPort(port);
        config.setTransports(Transport.WEBSOCKET);
        config.setPingInterval(5000);

        // Then initializes the Socket.IO instance.
        this.socketIOInstance = new SocketIOServer(config);

        // Enable event listeners.
        this.attachListeners();
    }

    /**
     * Binds various event listeners to the server.
     */
    private void attachListeners() {
        this.socketIOInstance.addConnectListener(new ConnectHandler(this));
        this.socketIOInstance.addDisconnectListener(new DisconnectHandler(this));
        this.socketIOInstance.addEventListener(PlayerPingHandler.EVENT_NAME, Integer.class, new PlayerPingHandler(this));
    }

    /**
     * Starts the game server.
     */
    public void startService() {
        gsLogger.info("Server is running on port {}", this.socketIOInstance.getConfiguration().getPort());
        this.socketIOInstance.start();
    }

    /**
     * Terminate all connections and stops execution of the game server.
     * <p>
     * All I/Os should be handled with care before shutting down!
     */
    public void terminateService() {
        gsLogger.info("Disconnecting players...");
        this.socketIOInstance.getBroadcastOperations().sendEvent("sv-force-kick");

        if (this.socketIOInstance.getAllClients().size() == 0) {
            gsLogger.info("Stopping server...");
            this.socketIOInstance.stop();
        }
    }

    /**
     * Logger object of the GameServer.
     * @return {@code Logger} object.
     */
    public Logger getLogger() {
        return gsLogger;
    }

    /**
     * SocketIOServer instance.
     * @return {@code SocketIOServer} object.
     */
    public SocketIOServer getSocketIOInstance() {
        return this.socketIOInstance;
    }

    /**
     * Debug mode of the GameServer.
     */
    public boolean getDebugMode() {
        return this.debugMode;
    }
}

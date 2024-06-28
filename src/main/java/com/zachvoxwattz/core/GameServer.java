package com.zachvoxwattz.core;

import java.util.concurrent.CompletableFuture;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.Transport;
import com.zachvoxwattz.handlers.PlayerPingHandler;
import com.zachvoxwattz.handlers.entry_exit.ConnectHandler;
import com.zachvoxwattz.handlers.entry_exit.DisconnectHandler;

/**
 * The main game server.
 * 
 * <p>Responsible for accepting incoming connections, processing information and uphold the game experience.
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
     * Keeps track of a lobby existence.
     */
    private boolean hasLobby = false;

    /**
     * Socket.IO instance for the server.
     */
    private SocketIOServer socketIOInstance;

    /**
     * Instance of a game lobby.
     */
    private GameLobby lobby;

    /**
     * Constructor initializing the main game server.
     * 
     * @param port Specifies the target port of which the server
     * should listen for incoming connections.
     * @param debugMode Regulates whether to enable {@code debug}
     * mode on the server. This argument is optional, by default,
     * omitting it results in value {@code false}.
     */
    public GameServer(int port, boolean debugMode) {
        this.debugMode = debugMode;

        // Constructs a Configuration object for starting the server.
        var config = new Configuration();
        config.setHostname("0.0.0.0");
        config.setPort(port);
        config.setTransports(Transport.WEBSOCKET);
        config.setPingInterval(10000);

        // Then initializes the Socket.IO instance.
        this.socketIOInstance = new SocketIOServer(config);

        // Enable event listeners.
        this.attachListeners();
    }

    /**
     * Broadcasts to all clients with given event name and datagram.
     * @param eventName String formatted. Specifies the event name to be broadcasted so that clients can listen to.
     * @param datagram The data object to be sent to clients if applicable.
     */
    public void broadcastEvent(String eventName, Object datagram) {
        this.socketIOInstance.getBroadcastOperations().sendEvent(eventName, datagram);
        if (this.debugMode) gsLogger.debug("Broadcasted event name '{}' to all listening clients.", eventName);
    }

    /**
     * Binds various event listeners to the server.
     * 
     * <p>Currently implemented listeners:
     * <ul>
     * <li>Connect
     * <li>Disconnect
     * <li>Ping
     * <li>TBU...
     * </ul>
     */
    private void attachListeners() {
        this.socketIOInstance.addConnectListener(new ConnectHandler(this));
        this.socketIOInstance.addDisconnectListener(new DisconnectHandler(this));
        this.socketIOInstance.addEventListener(PlayerPingHandler.REQ_EVENT_NAME, Integer.class, new PlayerPingHandler(this));
    }

    /**
     * Initializes a game lobby as requested from {@code ConnectHandler}.
     */
    public void createLobby() {
        this.lobby = new GameLobby(this);
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
        CompletableFuture<Void> disconnectPlayersTask = CompletableFuture.runAsync(() -> {
            this.socketIOInstance.getAllClients().forEach((client) -> { client.disconnect(); });
        });

        gsLogger.info("Stopping server...");
        disconnectPlayersTask.thenRun(() -> { this.socketIOInstance.stop(); });
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
     * Game lobby instance.
     * @return {@code GameLobby} object.
     */
    public GameLobby getLobby() {
        return this.lobby;
    }

    /**
     * Retrieves debug mode of the GameServer.
     * @return {@code true} if debug mode is enabled.
     */
    public boolean getDebugMode() {
        return this.debugMode;
    }

    /**
     * Retrieves lobby existence status.
     * @return {@code true} if the lobby has been created.
     */
    public boolean hasLobby() {
        return this.hasLobby;
    }

    /**
     * Sets lobby existence status.
     * @param value boolean of lobby existence status.
     */
    public void hasLobby(boolean value) {
        this.hasLobby = value;
    }
}

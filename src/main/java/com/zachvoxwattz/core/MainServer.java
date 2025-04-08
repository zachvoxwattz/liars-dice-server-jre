package com.zachvoxwattz.core;

import java.util.concurrent.CompletableFuture;

import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.Transport;

import com.zachvoxwattz.core.event_string.EventDataProvider;
import com.zachvoxwattz.core.logging.LogService;

import com.zachvoxwattz.handlers.auth.AuthTokenHandler;
import com.zachvoxwattz.handlers.connection.ConnectHandler;
import com.zachvoxwattz.handlers.connection.DisconnectHandler;
import com.zachvoxwattz.handlers.ping.UserPingHandler;

/**
 * The main game server.
 * 
 * <p>Responsible for accepting incoming connections, processing information and uphold the game experience.
 */
public class MainServer {
    /**
     * Max number of connections allowed.
     */
    public static int MAX_CONNECTED_CLIENTS = 6;
    
    /**
     * Server debug mode value.
     */
    private boolean debugMode;

    /**
     * Regulates whether the server is accepting new connections.
     */
    private boolean acceptConnections = true;

    /**
     * Keeps track of a lobby existence.
     */
    private boolean hasLobby = false;

    /**
     * The event string provider for both client request and server response events.
     */
    private EventDataProvider eventStringProvider;

    /**
     * Socket.IO instance for the server.
     */
    private SocketIOServer socketIOInstance;

    /**
     * Constructor initializing the main game server.
     * 
     * @param port Specifies the target port of which the server
     * should listen for incoming connections.
     * @param debugMode Regulates whether to enable {@code debug}
     * mode on the server. This argument is optional, by default,
     * omitting it results in value {@code false}.
     */
    public MainServer(int port, boolean debugMode) {
        this.debugMode = debugMode;
        
        // Initializes essential components first.
        this.eventStringProvider = new EventDataProvider();

        // Constructs a Configuration object for starting the server.
        var config = new Configuration();
        config.setHostname("0.0.0.0");
        config.setPort(port);
        config.setTransports(Transport.WEBSOCKET);
        config.setPingInterval(10000);
        config.setPingTimeout(45000);

        // Then initializes the Socket.IO instance.
        this.socketIOInstance = new SocketIOServer(config);

        // Attaches middlewares and listeners.
        this.attachListeners();
    }

    /**
     * Binds various event listeners to the server.
     * 
     * <p>Currently implemented listeners:
     * <ul>
     * <li>Connect.
     * <li>Disconnect.
     * <li>Ping.
     * <li>WebSocket Key provider.
     * <li>Player Registration.
     * <li>TBU...
     * </ul>
     */
    private void attachListeners() {
        // Listener for handling incoming connection.
        this.socketIOInstance.addConnectListener(new ConnectHandler(this));

        // Listener for handling every disconnection.
        this.socketIOInstance.addDisconnectListener(new DisconnectHandler(this));

        // Listener for handling Ping requests.
        var pingEventData = this.eventStringProvider.getEventData("cl-ping");
        this.socketIOInstance.addEventListener(
            pingEventData.getValue(), 
            Void.class,
            new UserPingHandler(this, true)
        );

        // Listener for handling auth token request.
        this.socketIOInstance.addEventListener(
            this.eventStringProvider.getEventString("cl-request-token"),
            Void.class,
            new AuthTokenHandler(this)
        );
    }

    /**
     * Initializes a game lobby as requested
     * from {@code ConnectHandler}.
     */
    public void createLobby() {
        /*
            Immediately sets the boolean property to true so as
            not to make this method called twice.
        */
        this.hasLobby = true;
    }

    /**
     * Starts the game server.
     */
    public void startService() {
        this.socketIOInstance.start();
        LogService.logInfo("Server is running on port %s", this.socketIOInstance.getConfiguration().getPort());
    }

    /**
     * Terminate all connections and stops execution of the game server.
     * <p>
     * All I/Os should be handled with care before shutting down!
     */
    public void terminateService() {
        CompletableFuture<Void> disconnectPlayersTask = null;
        var hasClients = false;

        /* 
            If there is at least one connected player, disconnect them.
            Otherwise, skips this operation.
         */
        if (this.getClientCount() > 0) {
            hasClients = true;
            LogService.logInfo("Disconnecting players...");
            
            disconnectPlayersTask = CompletableFuture.runAsync(() -> {
                this.socketIOInstance.getAllClients().forEach((client) -> { client.disconnect(); });
            });    
        }

        LogService.logInfo("Stopping server...");
        if (hasClients) disconnectPlayersTask.thenRun(() -> { this.socketIOInstance.stop(); });
        else this.socketIOInstance.stop();
    }

    /**
     * Broadcasts to all clients with given event name and datagram.
     * @param eventName String formatted. Specifies the event name to be broadcasted so that clients can listen to.
     * @param datagram The data object to be sent to clients if applicable.
     */
    public void broadcastEvent(String eventName, Object datagram) {
        this.socketIOInstance.getBroadcastOperations().sendEvent(eventName, datagram);
        LogService.logDebug("Broadcasted event name '%s' to all listening clients.", eventName);
    }

    /**
     * Returns the total number of connected clients.
     * @return Integer value of total count.
     */
    public int getClientCount() {
        return this.socketIOInstance.getAllClients().size();
    }

    /**
     * EventStringProvider instance.
     * @return {@code EventStringProvider} object.
     */
    public EventDataProvider getEventStringProvider() {
        return this.eventStringProvider;
    }

    /**
     * SocketIOServer instance.
     * @return {@code SocketIOServer} object.
     */
    public SocketIOServer getSocketIOInstance() {
        return this.socketIOInstance;
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

    /**
     * Retrieves server state of accepting new connections.
     * @return {@code true} if server is accepting connections.
     */
    public boolean acceptConnections() {
        return this.acceptConnections;
    }

    /**
     * Sets server state of accepting new connections.
     * @param value boolean of server state of accepting connections.
     */
    public void acceptConnections(boolean value) {
        this.acceptConnections = value;
    }
}

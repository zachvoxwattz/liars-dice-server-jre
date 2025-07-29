package com.zachvoxwattz.core;

import java.util.concurrent.CompletableFuture;

import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.Transport;

import com.zachvoxwattz.core.event_data_provider.EventDataProvider;
import com.zachvoxwattz.core.handlers.connectivity.ConnectHandler;
import com.zachvoxwattz.core.handlers.connectivity.DisconnectHandler;
import com.zachvoxwattz.core.handlers.middleware.SocketConnectionAuthorizationListener;
import com.zachvoxwattz.core.interfaces.ModuleAction;
import com.zachvoxwattz.core.logging.LogSentry;
import com.zachvoxwattz.core.shared.ServerConfigurations;

/**
 * <p>Responsible for accepting incoming and managing existing connections.
 */
public class ClientManager implements ModuleAction {
    /**
     * The main server object of the application.
     */
    private MainServer mainServer;

    /**
     * Socket.IO instance for the server.
     */
    private SocketIOServer socketIOInstance;

    /**
     * The event string provider for both client request and server response events.
     */
    private EventDataProvider eventStringProvider;

    /**
     * Regulates whether the server is accepting new connections.
     */
    private boolean acceptConnections = true;

    /**
     * Constructor for {@code ClientManager}
     * @param mainServer Main server object
     */
    public ClientManager(MainServer mainServer) {
        this.mainServer = mainServer;
        
        // Initializes essential components first.
        this.eventStringProvider = new EventDataProvider();

        // Constructs a Configuration object for starting the server.
        var config = new Configuration();
        config.setOrigin("*");
        config.setHostname("0.0.0.0");
        config.setPort(ServerConfigurations.PORT_NUMBER);
        config.setTransports(Transport.WEBSOCKET);
        config.setPingInterval(10000);
        config.setPingTimeout(45000);

        // Adds the authorization middleware.
        config.setAuthorizationListener(new SocketConnectionAuthorizationListener(this));

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
        this.socketIOInstance.addConnectListener(new ConnectHandler(this));
        this.socketIOInstance.addDisconnectListener(new DisconnectHandler(this));
    }

    /**
     * Broadcasts to all clients with given event name and datagram.
     * @param eventName String formatted. Specifies the event name to be broadcasted so that clients can listen to.
     * @param datagram The data object to be sent to clients if applicable.
     */
    public void broadcastEvent(String eventName, Object datagram) {
        this.socketIOInstance.getBroadcastOperations().sendEvent(eventName, datagram);
        LogSentry.logDebug("Broadcasted event name '%s' to all listening clients.", eventName);
    }

    /**
     * Starts the game server.
     */
    @Override
    public void initialize() {
        this.socketIOInstance.start();
        LogSentry.logInfo(
            "Server is running on port %s", 
            this.socketIOInstance.getConfiguration().getPort()
        );
    }

    /**
     * Terminate all connections and stops execution of the game server.
     * <p>
     * All I/Os should be handled with care before shutting down!
     */
    @Override
    public void shutdown() {
        CompletableFuture<Void> disconnectPlayersTask = null;
        var hasClients = false;

        /* 
            If there is at least one connected player, disconnect them.
            Otherwise, skips this operation.
         */
        if (this.getClientCount() > 0) {
            hasClients = true;
            LogSentry.logInfo("Disconnecting players...");
            
            disconnectPlayersTask = CompletableFuture.runAsync(() -> {
                this.socketIOInstance.getAllClients().forEach((client) -> { client.disconnect(); });
            });
        }

        LogSentry.logInfo("Stopping server...");
        if (hasClients) disconnectPlayersTask.thenRun(() -> { this.socketIOInstance.stop(); });
        else this.socketIOInstance.stop();
    }

    /**
     * Returns the total number of connected clients.
     * @return Integer value of total count.
     */
    public int getClientCount() {
        return this.socketIOInstance.getAllClients().size();
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

    /**
     * @return {@code EventStringProvider} object.
     */
    public EventDataProvider getEventStringProvider() {
        return this.eventStringProvider;
    }

    /**
     * @return {@code MainServer} object.
     */
    public MainServer getMainServer() {
        return this.mainServer;
    }
}

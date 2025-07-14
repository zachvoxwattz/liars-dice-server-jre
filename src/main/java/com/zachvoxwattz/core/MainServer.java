package com.zachvoxwattz.core;

/**
 * <p>The barebone of the entire application.
 * <p>Responsible for managing other core components.
 */
public class MainServer {
    /**
     * Major module for managing connected clients.
     */
    private ClientManager clientManager;

    /**
     * Constructor initializing the main game server.
     */
    public MainServer() {
        this.clientManager = new ClientManager(this);
    }

    /**
     * Runs the starting sequence of the application.
     */
    public void start() {
        this.clientManager.initialize();
    }

    /**
     * Runs the shutdown sequence of the application.
     */
    public void executeShutdownSequence() {
        this.clientManager.shutdown();
    }

    /**
     * @return {@code ClientManager} object.
     */
    public ClientManager getClientManager() {
        return this.clientManager;
    }
}

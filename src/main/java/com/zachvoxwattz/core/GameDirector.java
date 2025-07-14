package com.zachvoxwattz.core;

/**
 * <p>Responsible for processing information and upholding the game experience.
 */
public class GameDirector {
    /**
     * The main server object of the application.
     */
    private MainServer mainServer;
    
    /**
     * Constructor for {@code GameDirector}
     * @param mainServer Main server object
     */
    public GameDirector(MainServer mainServer) {
        this.mainServer = mainServer;
    }

    /**
     * @return {@code MainServer} object.
     */
    public MainServer getMainServer() {
        return this.mainServer;
    }
}

package com.zachvoxwattz.core;

import org.apache.logging.log4j.LogManager;

/**
 * Implemented Runnable hook used for shutting down the server gracefully.
 */
public class ShutdownThread extends Thread {
    /**
     * Main game server instance.
     */
    private GameServer mainGameServer;

    public ShutdownThread(GameServer mainGameServer) {
        this.mainGameServer = mainGameServer;
    }

    @Override
    public void run() {
        this.mainGameServer.getLogger().info("Shutdown hook invoked.");
        this.mainGameServer.terminateService();
        this.mainGameServer.getLogger().info("Shutdown sequence successful.");
        LogManager.shutdown();
    }
}

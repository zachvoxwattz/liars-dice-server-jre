package com.zachvoxwattz.core;

/**
 * Implemented Runnable hook used for shutting down the server gracefully.
 */
public class ShutdownHook implements Runnable {

    private GameServer mainGameServer;

    public ShutdownHook(GameServer mainGameServer) {
        this.mainGameServer = mainGameServer;
    }

    @Override
    public void run() {
        this.mainGameServer.getLogger().info("Shutdown Hook invoked. Terminating server...");
        this.mainGameServer.terminate();
    }
}

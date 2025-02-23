package com.zachvoxwattz.core;

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
        LogService.logInfo("Shutdown hook invoked.");
        this.mainGameServer.terminateService();
        LogService.logInfo("Shutdown sequence successful.");
    }
}

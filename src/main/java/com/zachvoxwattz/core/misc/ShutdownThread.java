package com.zachvoxwattz.core.misc;

import com.zachvoxwattz.core.MainServer;
import com.zachvoxwattz.core.logging.LogService;

/**
 * Implemented Runnable hook used for shutting down the server gracefully.
 */
public class ShutdownThread extends Thread {
    /**
     * Main game server instance.
     */
    private MainServer mainGameServer;

    public ShutdownThread(MainServer mainGameServer) {
        this.mainGameServer = mainGameServer;
    }

    @Override
    public void run() {
        LogService.logInfo("Shutdown hook invoked.");
        this.mainGameServer.terminateService();
        LogService.logInfo("Shutdown sequence successful.");
    }
}

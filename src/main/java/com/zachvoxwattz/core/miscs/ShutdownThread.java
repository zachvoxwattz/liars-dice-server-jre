package com.zachvoxwattz.core.miscs;

import com.zachvoxwattz.core.MainServer;
import com.zachvoxwattz.core.logging.LogSentry;

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
        LogSentry.logInfo("Shutdown hook invoked.");
        this.mainGameServer.executeShutdownSequence();
        LogSentry.logInfo("Shutdown sequence successful.");
    }
}

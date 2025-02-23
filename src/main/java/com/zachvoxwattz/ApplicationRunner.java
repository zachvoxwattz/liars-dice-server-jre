package com.zachvoxwattz;

import com.zachvoxwattz.core.GameServer;
import com.zachvoxwattz.core.LogService;
import com.zachvoxwattz.core.PortChecker;
import com.zachvoxwattz.core.ShutdownThread;
import com.zachvoxwattz.utils.ValueChecker;

/**
 * Entry point of the game server.
 */
public class ApplicationRunner {
    public static void main(String[] args) {
        // Declares default values for the configuration of the server.
        var serverPort = 11912;
        var debugMode = false;
        LogService.initialize();

        // Checks if there exists at least one input argument.
        if (args.length != 0) {
            // Processes the second passed argument as server's debug mode.
            if (ValueChecker.isBoolean(args[1])) {
                debugMode = Boolean.parseBoolean(args[1]);
                if (debugMode) LogService.logDebug("Server debug mode enabled.");
            }

            // Processes the first passed argument as server's target port.
            if (ValueChecker.isInteger(args[0])) {
                serverPort = Integer.parseInt(args[0]);
                if (debugMode) LogService.logDebug("Assigned port number %s to the server.", serverPort);
            }
            else {
                if (debugMode) LogService.logDebug("Assigned default port number %s to the server.", serverPort);
            }
        }

        // Performs port availability check.
        PortChecker.execute(serverPort, debugMode);

        // Initializes the server and the shutdown hook for graceful shutdown.
        var mainServer = new GameServer(serverPort, debugMode);

        // Attaches the shutdown hook to Runtime.
        Runtime.getRuntime().addShutdownHook(new ShutdownThread(mainServer));

        // Starts the server in the end.
        mainServer.startService();
    }
}

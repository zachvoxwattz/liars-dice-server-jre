package com.zachvoxwattz;

import com.zachvoxwattz.core.MainServer;
import com.zachvoxwattz.core.logging.LogService;
import com.zachvoxwattz.core.misc.PortChecker;
import com.zachvoxwattz.core.misc.ShutdownThread;
import com.zachvoxwattz.utils.ValueTypeValidator;

/**
 * Entry point of the game server.
 */
public class ApplicationRunner {
    // The main server object.
    private static MainServer mainServer;

    public static void main(String[] args) {
        // Initializes the logging service.
        LogService.initialize();

        // Declares default values for the configuration of the server.
        var serverPort = 11912;
        var debugMode = false;
        
        // Checks if there exists at least one input argument.
        if (args.length > 0) {
            // Processes the second passed argument as server's debug mode.
            if (ValueTypeValidator.isBoolean(args[1])) {
                debugMode = Boolean.parseBoolean(args[1]);
                LogService.setDebugMode(debugMode);
                LogService.logDebug("Server debug mode enabled.");
            }

            // Processes the first passed argument as server's target port.
            if (ValueTypeValidator.isInteger(args[0])) {
                serverPort = Integer.parseInt(args[0]);
                LogService.logDebug("Assigned port number %s to the server.", serverPort);
            }
            else {
                LogService.logDebug("Assigned default port number %s to the server.", serverPort);
            }
        }

        // Performs port availability check.
        PortChecker.execute(serverPort, debugMode);

        // Initializes the server and the shutdown hook for graceful shutdown.
        mainServer = new MainServer(serverPort, debugMode);

        // Attaches the shutdown hook to Runtime.
        Runtime.getRuntime().addShutdownHook(new ShutdownThread(mainServer));

        // Starts the server in the end.
        mainServer.startService();
    }
}

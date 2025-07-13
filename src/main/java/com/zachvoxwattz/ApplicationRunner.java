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
    /**
     * The main server object.
     */
    private static MainServer mainServer;

    /**
     * Denotes whether to use debug mode.
     */
    private static boolean debugMode = false;

    /**
     * Target port number to use.
     */
    private static int serverPort = 11912;

    /**
     * The very first entry point to this application.
     * @param args - List of string arguments
     */
    public static void main(String[] args) {
        // Initializes the logging service.
        LogService.initialize();
        
        // Parse the given args.
        parseArgs(args);

        // Performs port availability check.
        PortChecker.execute(serverPort, debugMode);

        // Initializes the server.
        initializeServer();
    }

    /**
     * Checks if there exists at least one input argument.
     * @param args - List of string arguments
     */
    protected static void parseArgs(String[] args) {
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
    }

    /**
     * Entry point of server initialization.
     */
    protected static void initializeServer() {
        // Initializes the server and the shutdown hook for graceful shutdown.
        mainServer = new MainServer(serverPort, debugMode);

        // Attaches the shutdown hook to Runtime.
        Runtime.getRuntime().addShutdownHook(new ShutdownThread(mainServer));

        // Starts the server in the end.
        mainServer.startService();
    }
}

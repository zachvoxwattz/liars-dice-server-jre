package com.zachvoxwattz;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.zachvoxwattz.core.GameServer;
import com.zachvoxwattz.core.PortChecker;
import com.zachvoxwattz.core.ShutdownHook;
import com.zachvoxwattz.utils.ValueChecker;

/**
 * Entry point of the game server.
 */
public class ServerRunner {
    private static Logger serverRunnerLogger = LogManager.getLogger("ServerRunner");

    public static void main(String[] args) {
        // Declares default values for the configuration of the server.
        var serverPort = 11912;
        var debugMode = false;
        serverRunnerLogger.info("Initializing server...");

        // Checks if there exists at least one input argument.
        if (args.length != 0) {
            // Processes the second passed argument as server's debug mode.
            if (ValueChecker.isBoolean(args[1])) {
                debugMode = Boolean.parseBoolean(args[1]);
                if (debugMode) serverRunnerLogger.debug("Server debug mode enabled.");
            }

            // Processes the first passed argument as server's target port.
            if (ValueChecker.isInteger(args[0])) {
                serverPort = Integer.parseInt(args[0]);
                if (debugMode) serverRunnerLogger.debug("Assigned port number {} to the server.", serverPort);
            }
            else {
                if (debugMode) serverRunnerLogger.debug("Assigned default port number {} to the server.", serverPort);
            }
        }

        // Performs port availability check.
        PortChecker.execute(serverPort, debugMode);
        serverRunnerLogger.info("Initialization completed. Starting server...");

        // Initializes the server and the shutdown hook for graceful shutdown.
        var mainServer = new GameServer(serverPort, debugMode);

        // Attaches the shutdown hook to Runtime.
        Runtime.getRuntime().addShutdownHook(new Thread(new ShutdownHook(mainServer)));

        // Starts the server in the end.
        mainServer.startService();
    }
}

package com.zachvoxwattz;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.zachvoxwattz.utils.ValueChecker;

/**
 * Entry point of the game server.
 */
public class ServerRunner {
    private static Logger mainLogger = LogManager.getLogger(ServerRunner.class);

    public static void main(String[] args) {
        // Declares default values for the configuration of the server.
        var serverPort = 11911;
        var debugMode = false;

        // Checks if there exists at least one input argument.
        if (args.length != 0) {
            // Processes the second passed argument as server's debug mode.
            if (ValueChecker.isBoolean(args[1])) {
                debugMode = Boolean.parseBoolean(args[1]);
                if (debugMode) {
                    mainLogger.trace("Server debug mode enabled.");
                    mainLogger.debug("Server debug mode enabled.");
                    mainLogger.info("Server debug mode enabled.");
                    mainLogger.error("Server debug mode enabled.");
                    mainLogger.warn("Server debug mode enabled.");
                    mainLogger.fatal("Server debug mode enabled.");
                }
            }
            // Processes the first passed argument as server's target port.
            if (ValueChecker.isInteger(args[0])) {
                serverPort = Integer.parseInt(args[0]);
            }
        }
    }
}
